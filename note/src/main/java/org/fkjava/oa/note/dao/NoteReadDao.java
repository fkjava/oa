package org.fkjava.oa.note.dao;

import java.util.Optional;

import org.fkjava.oa.identity.domain.User;
import org.fkjava.oa.note.domain.Note;
import org.fkjava.oa.note.domain.NoteRead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteReadDao extends JpaRepository<NoteRead, String> {

//	@Query("select new NoteRead(n, nr) "//
//			+ "    from Note n "//
//			// 使用外关联，查询读取记录
//			+ "        left outer join NoteRead nr on n.id = nr.note.id "//
//			// 过滤读取记录的阅读者
//			+ "        and nr.reader=:user"//
//			+ "    where n.status = 'PUBLISHED'") // 只查询已经发布的

	// 注意：虽然写了这个方法，但实际上调用NoteRead里面的命名查询
	@Query(name = "NoteRead.findByReader")
	Page<NoteRead> findByReader(@Param("user") User uesr, Pageable pageable);

	Optional<NoteRead> findByNoteAndReader(Note note, User user);

}
