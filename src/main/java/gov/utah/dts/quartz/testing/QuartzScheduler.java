package gov.utah.dts.quartz.testing;

import gov.utah.dts.MyJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by jjensen on 4/4/16.
 */

public class QuartzScheduler {

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void init() {


    }

}
