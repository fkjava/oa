package org.fkjava.oa.workflow.vo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProcessImage {

	private String name;
	private byte[] data;

	public ProcessImage(String name, byte[] data) {
		super();
		this.name = name;
		this.data = data;
	}

	public ProcessImage(String name, InputStream in) throws IOException {
		super();
		this.name = name;
		// this.data = data;
		// 把输入流里面的数据，写到输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		for (int len = in.read(buf); len != -1; len = in.read(buf)) {
			out.write(buf, 0, len);
		}
		this.data = out.toByteArray();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
