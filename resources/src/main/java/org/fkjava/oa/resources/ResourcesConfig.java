package org.fkjava.oa.resources;

import java.util.LinkedList;
import java.util.List;

import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourcesConfig {
	// 相当于是<filter>元素
	@Bean
	public ConfigurableSiteMeshFilter siteMeshFilter() {
		ConfigurableSiteMeshFilter filter = new ConfigurableSiteMeshFilter();
		// 在WEB-INF下面不能有sitemesh3.xml文件，
		// 普通WEB项目就是通过sitemesh3.xml来配置，但是Spring Boot里面不能用！
		return filter;
	}

	// 相当于<filter-mapping>元素
	@Bean
	public FilterRegistrationBean<ConfigurableSiteMeshFilter> myFilter() {
		FilterRegistrationBean<ConfigurableSiteMeshFilter> bean = new FilterRegistrationBean<>();

		// 过滤器的实现类
		bean.setFilter(siteMeshFilter());
		// 拦截哪些请求
		bean.addUrlPatterns("/*");
		// 是否支持异步请求
		bean.setAsyncSupported(true);
		// 排序顺序，0表示最高级，最先执行
		bean.setOrder(0);

		return bean;
	}
}
