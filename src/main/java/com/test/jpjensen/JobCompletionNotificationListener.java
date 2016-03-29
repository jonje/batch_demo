package com.test.jpjensen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jjensen on 3/24/16.
 *
 */
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static final Logger LOG = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final JdbcTemplate jdbcTemplate;
    private final String jobName;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate, String jobName) {
        this.jdbcTemplate = jdbcTemplate;
        this.jobName = jobName;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOG.info("!!! JOB Finished! Time to verify results");

            List<Person> results = jdbcTemplate.query("SELECT first_name, last_name FROM people", new RowMapper<Person>(){
                public Person mapRow(ResultSet rs, int row) throws SQLException {
                    return new Person(rs.getString(1), rs.getString(2));
                }
            });

            for (Person person : results) {
                LOG.info("Found <" + person + "> in the database.");
            }
        }
    }

    @BeforeJob
    public void beforeJob(JobExecution execution) {
        LOG.info(jobName + " starting at " + dateFormat.format(new Date()));
        LOG.info("Status: " + execution.getStatus().name() + " Running: " + execution.isRunning());
        JobHelper.JOBS.put(jobName, execution);


    }

}
