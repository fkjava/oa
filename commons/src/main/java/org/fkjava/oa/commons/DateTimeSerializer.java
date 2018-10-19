package org.fkjava.oa.commons;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateTimeSerializer extends JsonSerializer<Date> {

	private DateTimeConverter converer = new DateTimeConverter();

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		String str = converer.convert(value);
		gen.writeString(str);
	}

}
