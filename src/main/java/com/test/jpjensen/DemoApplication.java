package com.test.jpjensen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
//		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
//		System.out.println("Let's inspect teh beans provided by Spring Boot:");
//
//		String[] beanNames = ctx.getBeanDefinitionNames();
//		Arrays.sort(beanNames);
//		for (String beanName : beanNames) {
//			System.out.println(beanName);
//		}
		SpringApplication.run(DemoApplication.class, args);
	}
}
