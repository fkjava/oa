package org.fkjava.oa.storage.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "STORAGE_FILE_INFO")
public class FileInfo {

	@Id
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@GeneratedValue(generator = "uuid2")
	private String id;
	private String name;
	private String contentType;
	private Long contentLength;
	// 实际文件保存在哪个位置
	private String filePath;
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadTime;

	public FileInfo() {
	}

	public FileInfo(String name, String contentType, Long contentLength, String filePath) {
		super();
		this.name = name;
		this.contentType = contentType;
		this.contentLength = contentLength;
		this.filePath = filePath;
		this.uploadTime = new Date();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getContentLength() {
		return contentLength;
	}

	public void setContentLength(Long contentLength) {
		this.contentLength = contentLength;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
}
