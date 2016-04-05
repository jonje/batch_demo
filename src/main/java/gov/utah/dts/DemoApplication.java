package gov.utah.dts;

import gov.utah.dts.quartz.testing.EventHolderBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws Exception {
//		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
//		System.out.println("Let's inspect teh beans provided by Spring Boot:");
//
//		String[] beanNames = ctx.getBeanDefinitionNames();
//		Arrays.sort(beanNames);
//		for (String beanName : beanNames) {
//			System.out.println(beanName);
//		}

//        JobDetail job = JobBuilder.newJob(TestJob.class)
//                .withIdentity("dummyJobName", "group1").build();
//
//        Trigger trigger = TriggerBuilder
//                .newTrigger()
//                .withIdentity("dummyJobName", "group1")
//                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
//                .build();
//
//        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//        scheduler.start();
//        scheduler.scheduleJob(job, trigger);

		ConfigurableApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
        EventHolderBean bean = applicationContext.getBean(EventHolderBean.class);
        System.out.println("Event Processed?? - " + bean.getEventFired());
    }
}
