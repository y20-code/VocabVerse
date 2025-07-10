package com.yls.vocabverse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yls.vocabverse.mapper")
public class VocabverseApplication {

	public static void main(String[] args) {
		SpringApplication.run(VocabverseApplication.class, args);
	}

}
