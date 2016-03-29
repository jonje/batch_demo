package com.test.jpjensen;

import org.springframework.batch.core.JobExecution;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by jjensen on 3/28/16.
 */
public class JobHelper {
    public static final ConcurrentMap<String, JobExecution> JOBS = new ConcurrentHashMap<>();

}
