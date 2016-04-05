package gov.utah.dts.quartz.testing;

import gov.utah.dts.MyJob;
import gov.utah.jobs.DBConnectionFactory;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by jjensen on 4/5/16.
 */
@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(ContextRefreshedListener.class);

    @Autowired
    private EventHolderBean eventHolderBean;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Connection connection = DBConnectionFactory.newInstance("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/testpeople", "testuser", "password");
        Statement statement = null;

        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            statement = connection.createStatement();
            String sql = "select * from job";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String cron = resultSet.getString("cron");

                JobDetail job = JobBuilder.newJob(TestJob.class).withIdentity(name).build();
                Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
                job.getJobDataMap().put("Test", "Hi there");
                LOG.info(name + " scheduled");
                scheduler.scheduleJob(job, trigger);
            }
            LOG.info("All jobs scheduled.");
            scheduler.start();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

//        System.out.println("Context event Recieved");
//        eventHolderBean.setEventFired(true);
    }
}
