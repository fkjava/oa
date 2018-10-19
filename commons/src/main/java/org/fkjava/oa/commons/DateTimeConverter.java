package org.fkjava.oa.commons;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.util.StdConverter;

public class DateTimeConverter extends StdConverter<Date, String> {

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Override
	public String convert(Date value) {
		return format.format(value);
	}
}
