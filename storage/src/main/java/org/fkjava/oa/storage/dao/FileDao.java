package org.fkjava.oa.storage.dao;

import java.io.File;
import java.io.InputStream;

import org.fkjava.oa.storage.domain.FileInfo;

public interface FileDao {

	String save(String name, String contentType, long contentLength, InputStream content);

	File getFile(FileInfo info);

	void delete(FileInfo fileInfo);
}
