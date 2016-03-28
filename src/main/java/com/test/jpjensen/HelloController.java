package com.test.jpjensen;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jjensen on 3/23/16.
 */
@Controller
@RequestMapping("/")
public class HelloController {

    private static final String TEMPLATE = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public @ResponseBody Greeting index(@RequestParam(value="name", required = false, defaultValue = "Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
    }
}
