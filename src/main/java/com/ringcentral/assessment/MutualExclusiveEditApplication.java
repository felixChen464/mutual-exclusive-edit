package com.ringcentral.assessment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ringcentral.assessment.dao")
public class MutualExclusiveEditApplication {

	public static void main(String[] args) {
		SpringApplication.run(MutualExclusiveEditApplication.class, args);
	}

}
