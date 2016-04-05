package gov.utah.dts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jjensen on 3/29/16.
 */
@Service
public class CommandService {

    private static final Logger LOG = LoggerFactory.getLogger(CommandService.class);

    @Autowired
    ScheduledTasks tasks;

    public void processCommand(String jobName, String command) throws JobInterruptedException {
        switch (command) {
            case "START":
                tasks.runImportUsers();
                break;
            case "STOP":
                JobExecution execution = JobHelper.JOBS.get(jobName);
                if (execution != null) {
                    execution.stop();
                    throw new JobInterruptedException("Kill the job", BatchStatus.FAILED);
                }
                break;
        }
    }
}
