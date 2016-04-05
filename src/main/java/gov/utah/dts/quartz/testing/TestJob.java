package gov.utah.dts.quartz.testing;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jjensen on 4/4/16.
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class TestJob implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(TestJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap data = jobExecutionContext.getJobDetail().getJobDataMap();
        LOG.info("Job " + jobExecutionContext.getJobDetail().getKey().toString() + " executed at " + jobExecutionContext.getFireTime());
        LOG.info(data.getString("Test"));



    }
}
