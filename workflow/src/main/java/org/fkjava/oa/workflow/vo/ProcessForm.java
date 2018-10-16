package org.fkjava.oa.workflow.vo;

import org.activiti.engine.form.FormData;
import org.activiti.engine.repository.ProcessDefinition;

/**
 * VO : Value Object，用于封装在各层之间传递的数据。
 * 
 * @author lwq
 *
 */
public class ProcessForm {
	/**
	 * 流程定义
	 */
	private ProcessDefinition definition;
	/**
	 * 表单内容。大部分情况下表单内容其实是String类型的，默认表单引擎也只是返回String的。<br/>
	 * 表单文件的内容。
	 */
	private Object content;
	/**
	 * 表单数据
	 */
	private FormData data;
	/**
	 * 表单文件的文件名
	 */
	private String formKey;

	public ProcessDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(ProcessDefinition definition) {
		this.definition = definition;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public FormData getData() {
		return data;
	}

	public void setData(FormData data) {
		this.data = data;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
}
