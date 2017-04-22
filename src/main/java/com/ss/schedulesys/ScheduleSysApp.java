package com.ss.schedulesys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.ss.schedulesys.config.ScheduleSysProperties;

/**
 * @author ezerbo
 *
 */
@SpringBootApplication(scanBasePackages =  "com.ss.schedulesys")
@EnableConfigurationProperties(value = {ScheduleSysProperties.class})
public class ScheduleSysApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SpringApplication(ScheduleSysApp.class)
			.run(args);
	}

}