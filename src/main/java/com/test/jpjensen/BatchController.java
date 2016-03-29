package com.test.jpjensen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jpjensen on 3/28/16.
 *
 * Controller for all of the batch processing requests.
 */
@Controller
@RequestMapping("/batch")
public class BatchController {

    private static final Logger LOG = LoggerFactory.getLogger(BatchController.class);

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Autowired
    private CommandService commandService;

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public @ResponseBody
    List<JobStatusDTO> allStats() {

        return JobHelper.JOBS.keySet().stream().map(key -> new JobStatusDTO(JobHelper.JOBS.get(key), key)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/stats/{jobName}", method = RequestMethod.GET)
    public @ResponseBody
    JobStatusDTO statsByJob(@PathVariable String jobName) {
        JobExecution jobExecution = JobHelper.JOBS.get(jobName);
        JobStatusDTO jobStatusDTO;

        if(jobExecution != null) {
            jobStatusDTO = new JobStatusDTO(jobExecution, jobName);

        } else {
            jobStatusDTO = new JobStatusDTO();
        }

        return jobStatusDTO;
    }

    @RequestMapping(value = "/{jobName}/{command}", method = RequestMethod.GET)
    public @ResponseBody String runCommand(@PathVariable String jobName, @PathVariable String command) {
        try {
            commandService.processCommand(jobName, command);
        } catch (JobInterruptedException e) {
            LOG.error("Batch job manually stopped.");
        }
        return "Success";
    }

    @RequestMapping(value = "/job-names", method = RequestMethod.GET)
    public @ResponseBody Set<String> jobNames() {
        return JobHelper.JOBS.keySet();
    }
}
