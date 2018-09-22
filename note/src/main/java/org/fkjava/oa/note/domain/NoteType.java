package org.fkjava.oa.note.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.fkjava.oa.commons.domain.UUIDEntity;

@Entity
@Table(name = "note_type")
public class NoteType extends UUIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String name;
	private boolean modifiable = false;
	private boolean deletable = false;
	private boolean revocable = false;
	// 排序的序号
	private double number = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isModifiable() {
		return modifiable;
	}

	public void setModifiable(boolean modifiable) {
		this.modifiable = modifiable;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public boolean isRevocable() {
		return revocable;
	}

	public void setRevocable(boolean revocable) {
		this.revocable = revocable;
	}

	public double getNumber() {
		return number;
	}

	public void setNumber(double number) {
		this.number = number;
	}
}
