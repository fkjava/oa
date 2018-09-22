package org.fkjava.oa.note.service;

import java.util.List;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.note.domain.Note;
import org.fkjava.oa.note.domain.NoteType;

public interface NoteService {

	void save(NoteType type);

	List<NoteType> findAllTypes();

	Result deleteType(String id);

	Result save(Note note);

}
