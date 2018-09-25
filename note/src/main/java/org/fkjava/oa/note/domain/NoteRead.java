package org.fkjava.oa.note.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.fkjava.oa.commons.domain.UUIDEntity;
import org.fkjava.oa.identity.domain.User;

@Entity
@Table(name = "NOTE_READ")
// 命名查询，名字规则：实体类.方法名
@NamedQuery(name = "NoteRead.findByReader", query = "select new NoteRead(n, nr) "//
		+ "    from Note n "//
		// 使用外关联，查询读取记录
		+ "        left outer join NoteRead nr on n.id = nr.note.id "//
		// 过滤读取记录的阅读者
		+ "        and nr.reader=:user"//
		+ "    where n.status = 'PUBLISHED'")
public class NoteRead extends UUIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "reader_id")
	private User reader;
	@Temporal(TemporalType.TIMESTAMP)
	private Date readTime;
	@ManyToOne
	@JoinColumn(name = "note_id")
	private Note note;

	public NoteRead() {
	}

	// 使用自定义的构造器来创建查询对象
	public NoteRead(Note note, NoteRead noteRead) {
		if (noteRead != null) {
			super.setId(noteRead.getId());
			this.reader = noteRead.reader;
			this.readTime = noteRead.readTime;
		}
		this.note = note;
	}

	public User getReader() {
		return reader;
	}

	public void setReader(User reader) {
		this.reader = reader;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}
}
