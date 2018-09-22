package org.fkjava.oa.note.dao;

import org.fkjava.oa.note.domain.NoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteTypeDao extends JpaRepository<NoteType, String> {

	NoteType findByName(String name);

}
