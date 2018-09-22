package org.fkjava.oa.note.dao;

import org.fkjava.oa.note.domain.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteDao extends JpaRepository<Note, String> {

	Page<Note> findByTitleContainingOrderByStatusAscTitleAsc(String keyword, Pageable pageable);

}