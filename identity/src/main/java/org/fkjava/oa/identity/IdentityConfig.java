package org.fkjava.oa.identity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration // 自动配置
@SpringBootApplication // 表示这是一个Spring Boot的应用
@ComponentScan("org.fkjava")
@EnableTransactionManagement // 激活事务。但是使用JPA其实已经激活了事务！
public class IdentityConfig {

	public static void main(String[] args) {
		SpringApplication.run(IdentityConfig.class, args);
	}
}
