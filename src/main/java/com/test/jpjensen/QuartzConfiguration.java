package com.test.jpjensen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by jjensen on 3/28/16.
 */


public class QuartzConfiguration {
//    @Bean
//    public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean() {
//        MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
//        obj.setTargetBeanName("jobone");
//        obj.setTargetMethod("myTask");
//
//        return obj;
//    }
//
//    // Job is scheduled for 3 + 1 times with the interval of 30 seconds
//    @Bean
//    public SimpleTriggerFactoryBean simpleTriggerFactoryBean() {
//        SimpleTriggerFactoryBean stFactory = new SimpleTriggerFactoryBean();
//        stFactory.setJobDetail(methodInvokingJobDetailFactoryBean().getObject());
//        stFactory.setStartDelay(3000);
//        stFactory.setRepeatInterval(3000);
//        stFactory.setRepeatCount(10);
//
//        return stFactory;
//    }

    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(BatchConfiguration.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "RAM");
        map.put(MyJobTwo.COUNT, 1);
        factory.setJobDataAsMap(map);
        factory.setGroup("batchjobs");
        factory.setName("batch1");
        return factory;
    }

    // Job is scheduled after every 1 minute

    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(jobDetailFactoryBean().getObject());
        stFactory.setStartDelay(3000);
        stFactory.setName("mytrigger");
        stFactory.setGroup("mygroup");
        stFactory.setCronExpression("0/5 * * * * ? *");
        return stFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(cronTriggerFactoryBean().getObject());
        return scheduler;
    }



}
