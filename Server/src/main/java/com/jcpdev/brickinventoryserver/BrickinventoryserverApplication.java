package com.jcpdev.brickinventoryserver;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BrickinventoryserverApplication {

	public static void main(String[] args)  {
		SpringApplication.run(BrickinventoryserverApplication.class, args);

	}

}
