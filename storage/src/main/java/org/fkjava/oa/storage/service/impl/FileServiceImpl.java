package org.fkjava.oa.storage.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;

import org.fkjava.oa.storage.dao.FileDao;
import org.fkjava.oa.storage.dao.FileInfoDao;
import org.fkjava.oa.storage.domain.FileInfo;
import org.fkjava.oa.storage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FileServiceImpl implements FileService {

	// 对文件进行操作的DAO
	@Autowired
	private FileDao fileDao;
	// 对文件信息进行操作的DAO
	@Autowired
	private FileInfoDao fileInfoDao;

	@Override
	public void save(String name, String contentType, long contentLength, InputStream content) {
		// 保存文件到硬盘的某个目录，返回一个保存的文件路径回来
		// 路径是相对于某个目录的！
		String filePath = fileDao.save(name, contentType, contentLength, content);
		// 保存文件信息到数据库
		FileInfo fileInfo = new FileInfo(name, contentType, contentLength, filePath);
		fileInfoDao.save(fileInfo);
	}

	@Override
	public Page<FileInfo> find(String name, String orderByProperty, Integer pageNumber) {
		// 排序条件
		Sort sort = Sort.by(Order.asc(orderByProperty));
		// 分页条件
		Pageable pageable = PageRequest.of(pageNumber, 10, sort);

		Page<FileInfo> page;
		if (StringUtils.isEmpty(name)) {
			// 没有name参数
			page = this.fileInfoDao.findAll(pageable);
		} else {
			// 要自己加%
			// name = "%" + name + "%";
			// page = this.fileInfoDao.findByNameLike(name, pageable);

			// 不需要自己加%，Spring Data会自动前后加%
			page = this.fileInfoDao.findByNameContaining(name, pageable);
		}
		return page;
	}

	@Override
	public FileInfo getFileInfoById(String id) {
		Optional<FileInfo> optional = this.fileInfoDao.findById(id);
		// 如果有FileInfo对象，返回查询得到的FileInfo；如果没有查询到对象，则返回null
		return optional.orElse(null);
	}

	@Override
	public File getFile(FileInfo info) {
		return this.fileDao.getFile(info);
	}

	@Override
	public void delete(String id) {
		// 1.根据id查询FileInfo
		FileInfo fileInfo = this.getFileInfoById(id);
		if (fileInfo != null) {
			// 2.根据FileInfo里面的路径，删除文件
			fileDao.delete(fileInfo);
			// 3.删除FileInfo
			this.fileInfoDao.delete(fileInfo);
		}
	}
}
