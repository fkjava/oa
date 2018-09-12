package org.fkjava.oa.identity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration // 自动配置
@SpringBootApplication // 表示这是一个Spring Boot的应用
public class IdentityConfig {

	public static void main(String[] args) {
		SpringApplication.run(IdentityConfig.class, args);
	}
}
