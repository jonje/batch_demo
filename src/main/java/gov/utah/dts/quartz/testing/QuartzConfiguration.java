package gov.utah.dts.quartz.testing;

import org.quartz.CronTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.List;

/**
 * Created by jjensen on 4/4/16.
 */
@Configuration
public class QuartzConfiguration {
//
//    @Bean(name="scheduler")
//    @Scope(value="prototype")
//    public SchedulerFactoryBean schedulerFactoryBean(List<CronTrigger> triggerFactoryBeans) {
//        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
//        CronTrigger[] cronTriggers = new CronTrigger[triggerFactoryBeans.size()];
//        cronTriggers = triggerFactoryBeans.toArray(cronTriggers);
//        scheduler.setTriggers(cronTriggers);
//        return scheduler;
//    }

}
