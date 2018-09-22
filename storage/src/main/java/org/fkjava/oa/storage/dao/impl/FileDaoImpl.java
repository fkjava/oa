package org.fkjava.oa.storage.dao.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.fkjava.oa.storage.dao.FileDao;
import org.fkjava.oa.storage.domain.FileInfo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

@Repository
public class FileDaoImpl implements FileDao, InitializingBean {

	// 这就是【某个目录】，该路径应该是通过配置文件注入的
	private File dir = new File("/tmp/storage/shared/file");

	@Override
	public void afterPropertiesSet() throws Exception {
		if (!dir.exists()) {
			// 如果目录不存在，创建目录
			dir.mkdirs();
		}
	}

	// 保存文件内容到某个目录，返回文件的相对路径，相对于【某个目录】
	@Override
	public String save(String name, String contentType, long contentLength, InputStream content) {
		// 一般文件上传都会使用随机文件名，避免不同的用户上传同名文件
		String fileName = UUID.randomUUID().toString();

		// 存储目标
		File file = new File(dir, fileName);

		// 把内容写入目标
		// StandardCopyOption是标准复制选项
		// REPLACE_EXISTING表示如果目标文件存在，直接覆盖掉
		// COPY_ATTRIBUTES复制文件的属性，比如文件创建时间之类的
		try {
			Files.copy(content, file.toPath(), //
					StandardCopyOption.REPLACE_EXISTING//
			// , StandardCopyOption.COPY_ATTRIBUTES//
			);
		} catch (IOException e) {
			throw new RuntimeException("无法保存文件到硬盘:" + e.getLocalizedMessage(), e);
		}
		return fileName;
	}

	@Override
	public File getFile(FileInfo info) {
		return new File(dir, info.getFilePath());
	}

	@Override
	public void delete(FileInfo fileInfo) {
		File file = new File(dir, fileInfo.getFilePath());
		file.delete();
	}
}
