package com.test.jpjensen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jjensen on 3/23/16.
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    private static final String TEMPLATE = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private static JobExplorer jobExplorer;

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public @ResponseBody Greeting index(@RequestParam(value="name", required = false, defaultValue = "Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
    }


}
