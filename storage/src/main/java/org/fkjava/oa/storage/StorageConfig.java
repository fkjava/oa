package org.fkjava.oa.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration // 自动配置
@SpringBootApplication // 表示这是一个Spring Boot的应用
@ComponentScan("org.fkjava")
public class StorageConfig {

	public static void main(String[] args) {
		SpringApplication.run(StorageConfig.class, args);
	}
}
