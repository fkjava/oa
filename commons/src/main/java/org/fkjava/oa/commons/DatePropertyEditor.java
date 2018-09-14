package org.fkjava.oa.commons;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义的Date转换器
 * 
 * @author lwq
 *
 */
public class DatePropertyEditor extends PropertyEditorSupport {

	// Date和String转换的工具
	private SimpleDateFormat dateFormat;

	public DatePropertyEditor(String pattern) {
		dateFormat = new SimpleDateFormat(pattern);
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			Date date = dateFormat.parse(text);
			// 因为不知道具体的实体对象，所以转换的时候，不是直接设置给对象，而是给转换核心工具自己去负责
			// 根据需要的数据类型，找到转换器，转换以后核心逻辑会调用getValue()方法把转换后的值获取出来设置给目标对象
			super.setValue(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException("字符串无法转换为Date: " + e.getMessage(), e);
		}
	}
}
