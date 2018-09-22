package org.fkjava.oa.storage.service;

import java.io.File;
import java.io.InputStream;

import org.fkjava.oa.storage.domain.FileInfo;
import org.springframework.data.domain.Page;

public interface FileService {

	String save(String name, String contentType, long contentLength, InputStream content);

	Page<FileInfo> find(String name, String orderByProperty, Integer pageNumber);

	FileInfo getFileInfoById(String id);

	File getFile(FileInfo info);

	void delete(String id);

}
