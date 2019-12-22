package com.qf.qfv2background;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jmx.support.RegistrationPolicy;

@SpringBootApplication
@EnableDubbo
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class QfV2BackgroundApplication {

	public static void main(String[] args) {
		SpringApplication.run(QfV2BackgroundApplication.class, args);
	}

}
