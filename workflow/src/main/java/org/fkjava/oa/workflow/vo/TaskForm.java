package org.fkjava.oa.workflow.vo;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.fkjava.oa.commons.domain.BusinessData;

public class TaskForm extends ProcessForm {

	// 流程实例，Historic开头的表示历史数据，历史数据比运行时数据要完整
	private HistoricProcessInstance instance;
	// 待办任务
	private Task task;
	// 业务数据
	private BusinessData businessData;

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public HistoricProcessInstance getInstance() {
		return instance;
	}

	public void setInstance(HistoricProcessInstance instance) {
		this.instance = instance;
	}

	public BusinessData getBusinessData() {
		return businessData;
	}

	public void setBusinessData(BusinessData businessData) {
		this.businessData = businessData;
	}
}
