package org.fkjava.oa.workflow;

import javax.sql.DataSource;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration // 自动配置
@SpringBootApplication // 表示这是一个Spring Boot的应用
@ComponentScan("org.fkjava")
@EnableTransactionManagement // 激活事务。但是使用JPA其实已经激活了事务！
public class WorkflowConfig {

	// 1.配置流程引擎
	@Bean
	public SpringProcessEngineConfiguration processEngineConfiguration(//
			// 注入数据源
			@Autowired DataSource dataSource//
			, @Autowired PlatformTransactionManager transactionManager// 注入事务管理器
	//
	) {
		SpringProcessEngineConfiguration cfg = new SpringProcessEngineConfiguration();

		// 设置数据源
		cfg.setDataSource(dataSource);
		// 设置事务管理器
		cfg.setTransactionManager(transactionManager);
		// 是否自动创建数据库的表
		// MyBatis不能自动创建表，本质上是一堆的SQL语句
		cfg.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		// 是否激活定时任务，一般测试的时候不激活
		cfg.setJobExecutorActivate(false);
		// 如果要扩展流程，所有的扩展都通过ProcessEngineConfiguration注入

		return cfg;
	}

	// 2.使用工厂Bean的方式配置流程引擎实例
	@Bean(autowire = Autowire.BY_TYPE)
	public ProcessEngineFactoryBean processEngine() {
		ProcessEngineFactoryBean bean = new ProcessEngineFactoryBean();
		// bean.setProcessEngineConfiguration(cfg);
		return bean;
	}

	// 3.获取流程引擎里面的核心服务
	// 存储服务，负责流程定义的管理，部署、禁用、激活、查询等
	// 流程定义是画图的时候生成的bpmn文件，本质上是个XML文件。
	@Bean
	public RepositoryService repositoryService(@Autowired ProcessEngine processEngine) {
		return processEngine.getRepositoryService();
	}

	// 运行时服务，负责启动流程实例
	// 根据流程定义产生的实例
	@Bean
	public RuntimeService runtimeService(@Autowired ProcessEngine processEngine) {
		return processEngine.getRuntimeService();
	}

	// 表单服务，负责渲染表单的内容，所有跟表单有关的操作，都通过此服务来执行
	@Bean
	public FormService formService(@Autowired ProcessEngine processEngine) {
		return processEngine.getFormService();
	}

	// 历史服务，所有在运行时的数据，使用完以后直接删除
	// 但是全部的详细信息，都在历史服务里面有完整记录
	// 删除运行时数据的目的：让运行时数据比较少，运行效率比较高
	@Bean
	public HistoryService historyService(@Autowired ProcessEngine processEngine) {
		return processEngine.getHistoryService();
	}

	// 待办任务服务，负责多人协同的时候使用。根据人查询待办任务、完成任务、转派任务……
	@Bean
	public TaskService taskService(@Autowired ProcessEngine processEngine) {
		return processEngine.getTaskService();
	}

	// 用户权限服务，使用非常少。因为我们本身就有用户权限服务
	@Bean
	public IdentityService activitiIdentityService(@Autowired ProcessEngine processEngine) {
		return processEngine.getIdentityService();
	}

	public static void main(String[] args) {
		SpringApplication.run(WorkflowConfig.class, args);
	}
}
