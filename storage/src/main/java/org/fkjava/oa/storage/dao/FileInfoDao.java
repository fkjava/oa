package org.fkjava.oa.storage.dao;

import java.util.List;

import org.fkjava.oa.storage.domain.FileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// <FileInfo, String> : 第一个参数表示要操作哪个表/实体类，第二个参数表示主键是什么类型的。
@Repository
public interface FileInfoDao extends JpaRepository<FileInfo, String> {

	// where name like '参数%'
	List<FileInfo> findByNameEndingWith(String string);

	// where name like '参数%' OR contentLength > 参数
	List<FileInfo> findByNameEndingWithOrContentLengthGreaterThan(String string, long i);

	// 第一种表示第一个参数
	// @Query("from FileInfo where name like ?1")
	// 第二种表示使用命名参数
	@Query("from FileInfo where name like :name")
	List<FileInfo> findByHQL(@Param("name") String string);

	// Spring Data会自动根据方法的参数、返回值类型决定如何来处理该方法名
	Page<FileInfo> findByNameContaining(String name, Pageable pageable);

	// save方法不需要自己写，继承下来的接口已经有了！
	// void save(FileInfo fileInfo);

}
