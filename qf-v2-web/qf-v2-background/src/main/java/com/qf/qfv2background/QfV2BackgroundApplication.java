package com.qf.qfv2background;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class QfV2BackgroundApplication {

	public static void main(String[] args) {
		SpringApplication.run(QfV2BackgroundApplication.class, args);
	}

}
