package org.fkjava.oa.note.service;

import java.util.List;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.note.domain.Note;
import org.fkjava.oa.note.domain.NoteRead;
import org.fkjava.oa.note.domain.NoteType;
import org.springframework.data.domain.Page;

public interface NoteService {

	void save(NoteType type);

	List<NoteType> findAllTypes();

	Result deleteType(String id);

	Result save(Note note);

	Page<Note> findNotes(String keyword, Integer pageNumber);

	void publish(String id);

	void revoke(String id, String revokeRemark);

	Page<NoteRead> findMyNotes(String keyword, String orderByProperty, String orderByDirection, Integer pageNumber);

	NoteRead findNoteReadByNoteId(String id);

	void read(String id);

}
