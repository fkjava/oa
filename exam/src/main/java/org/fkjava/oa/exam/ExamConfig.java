package org.fkjava.oa.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 自动配置
@SpringBootApplication // 表示这是一个Spring Boot的应用
@ComponentScan("org.fkjava")
public class ExamConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 添加首页链接
        registry.addViewController("/exam").setViewName("exam/index");
        registry.addViewController("/exam/").setViewName("exam/index");

        //试题管理
        registry.addViewController("/exam/item").setViewName("exam/item/index");
        //试卷管理
        registry.addViewController("/exam/paper").setViewName("exam/paper/index");
        // 批改
        registry.addViewController("/exam/correct").setViewName("exam/correct/index");
        // 统计分析
        registry.addViewController("/exam/analysis").setViewName("exam/analysis/index");
    }

    public static void main(String[] args) {
		SpringApplication.run(ExamConfig.class, args);
	}
}
