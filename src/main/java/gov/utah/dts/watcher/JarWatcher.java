package gov.utah.dts.watcher;

import gov.utah.dts.PropertyFile;
import gov.utah.jobs.DBConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Created by jjensen on 4/5/16.
 */
public class JarWatcher implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(JarWatcher.class);
    private boolean running;

    @Override
    public void run() {
        running = true;
        LOG.info("Directory Watcher Started");
        Path directory = Paths.get(System.getProperty("user.home"),"jars");

        try {
            WatchService watchService = directory.getFileSystem().newWatchService();
            directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
            WatchKey watchKey;

            while (running) {
                watchKey = watchService.take();
                List<WatchEvent<?>> events = watchKey.pollEvents();

                for (WatchEvent event : events) {
                    handleEvent(event, directory);
                }

                watchKey.reset();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void handleEvent(WatchEvent event, Path directory) throws IOException {
        String fileName = event.context().toString();

        if (fileName.endsWith(".jar")) {
            if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                LOG.info(fileName + " added to jars directory");
                Path filePath = Paths.get(directory.toString(), fileName);
                URL[] urls = new URL[] {
                        filePath.toUri().toURL()
                };
                URLClassLoader loader = new URLClassLoader(urls);
                URL url = loader.findResource("META-INF/MANIFEST.MF");
                Manifest manifest = new Manifest(url.openStream());
                Attributes nameAttributes = manifest.getMainAttributes();
                String jobName = nameAttributes.getValue("Job-Name");
                String cron = nameAttributes.getValue("Cron");

                if (jobName != null && cron != null) {
                    Connection connection = DBConnectionFactory.newInstance("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/testpeople", "testuser", "password");
                    try {
                        createOrUpdateJob(connection, jobName, cron, fileName);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            } else if(event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                LOG.info(fileName + " removed from jars directory");

            }

        }
    }

    private void createOrUpdateJob(Connection connection, String jobName, String cron, String jarName) throws SQLException {
        try {
            if (jobExists(connection, jobName)) {
                updateJob(connection, jobName, cron, jarName);
            } else {
                createJob(connection, jobName, cron, jarName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }

    private void createJob(Connection connection, String jobName, String cron, String jarName) {
        String sql = "insert into job (name, jar_name, cron) values(?,?,?)";
        LOG.info("Adding " + jobName + " to the database");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, jobName);
            preparedStatement.setString(2, jarName);
            preparedStatement.setString(3, cron);
            preparedStatement.execute();

        } catch (SQLException e) {
            LOG.error("There was a problem creating job " + jobName + " in the database.");
            LOG.debug("Stacktrace:");
            for(StackTraceElement stackTraceElement : e.getStackTrace()) {
                LOG.debug(stackTraceElement.toString());
            }

        }
    }

    private void updateJob(Connection connection, String jobName, String cron, String jarName) {
        String sql = "update job set cron = ?, jar_name = ? where name = ?";
        LOG.info("Updating " + jobName + " to the database");

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cron);
            preparedStatement.setString(2, jarName);
            preparedStatement.setString(3, jobName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("There was a problem updating job " + jobName + " in the database");
            LOG.debug("Stacktrace: ");
            for(StackTraceElement stackTraceElement : e.getStackTrace()) {
                LOG.debug(stackTraceElement.toString());
            }
        }
    }

    private boolean jobExists(Connection connection, String jobName) throws SQLException {
        String sql = "Select * from job where name = ?";
        LOG.info("Checking if " + jobName + " exists");

        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, jobName);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            LOG.error("Error with statement");
            LOG.debug("Stacktrace: ");
            for(StackTraceElement stackTraceElement : e.getStackTrace()) {
                LOG.debug(stackTraceElement.toString());
            }
        }

        return resultSet.next();
    }
}
