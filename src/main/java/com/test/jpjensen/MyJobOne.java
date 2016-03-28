package com.test.jpjensen;

import org.springframework.stereotype.Service;

/**
 * Created by jjensen on 3/28/16.
 */
@Service("jobone")
public class MyJobOne {
    protected void myTask() {
        System.out.println("This is my task");
    }
}
