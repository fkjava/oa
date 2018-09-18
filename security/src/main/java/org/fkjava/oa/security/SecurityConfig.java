package org.fkjava.oa.security;

import org.fkjava.oa.security.service.impl.MyAccessDecisionManager;
import org.fkjava.oa.security.service.impl.MyFilterSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 自动配置
@SpringBootApplication // 表示这是一个Spring Boot的应用
@ComponentScan("org.fkjava")
//@EnableTransactionManagement
//@EnableWebMvc //在Spring Boot项目里面，不要激活此选项，会自动配置的。
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	@Autowired
	private FilterInvocationSecurityMetadataSource securityMetadataSource;

	// 增加自定义的视图控制器，当一些页面只是简单的页面、不需要控制器类的时候，就可以通过这种方式配置路径
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/security/login")// 访问的URL
				.setViewName("security/login")// 视图的路径，配合视图解析器
		;
	}

	// 配置Spring Security
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super.configure(http);
		http.formLogin()// 表单登录的配置
				.loginPage("/security/login")// 登录页面的URL
				.loginProcessingUrl("/security/do-login")// 处理登录的URL
				.usernameParameter("loginName")// 登录名的字段名
				.passwordParameter("password")// 密码的字段名
				.and().logout() // 退出登录的配置
				.logoutUrl("/security/do-logout")// 退出登录的URL
				.logoutSuccessUrl("/")// 退出登录之后的URL
				.and().csrf()// 防止跨站攻击，请求参数中需要携带一个特殊的隐藏属性
				.and().authorizeRequests()// 配置鉴权（看用户是否有权限访问目标）
				.antMatchers("/security/login", "/webjars/**", "/static/**").permitAll()// 所有人无须登录可以访问的页面
				.anyRequest().hasAnyRole("USER")// 其他所有链接，都必须登录后具有USER身份可以访问
				.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
					@Override
					public <O extends FilterSecurityInterceptor> O postProcess(O object) {
						object.setSecurityMetadataSource(securityMetadataSource);
						object.setAccessDecisionManager(accessDecisionManager());
						return object;
					}
				})//
		;

	}

	@Bean
	public FilterInvocationSecurityMetadataSource securityMetadataSource() {
		return new MyFilterSecurityMetadataSource();
	}

	@Bean
	public AccessDecisionManager accessDecisionManager() {
		return new MyAccessDecisionManager();
	}

	public static void main(String[] args) {
		SpringApplication.run(SecurityConfig.class, args);
	}
}
