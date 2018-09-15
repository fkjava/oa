package org.fkjava.oa.identity.vo;

import java.io.Serializable;

// VO : Value Object，用于在各个层之间传递值的时候，封装多个值使用的。
// domain/bean/entity/pojo : 用于持久化保存的对象
public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int STATUS_OK = 2;
	public static final int STATUS_ERROR = 3;
	public static final int STATUS_WARN = 1;
	/**
	 * 操作状态
	 */
	private int status;
	/**
	 * 提示信息
	 */
	private String message;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}