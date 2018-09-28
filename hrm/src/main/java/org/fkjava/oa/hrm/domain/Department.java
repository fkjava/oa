package org.fkjava.oa.hrm.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.fkjava.oa.commons.domain.UUIDEntity;
import org.fkjava.oa.identity.domain.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "hrm_department")
public class Department extends UUIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	@JsonIgnore
	private Department parent;

	@OneToMany(mappedBy = "parent")
	private List<Department> children;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;
	// 排序的序号
	private Double number;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public List<Department> getChildren() {
		return children;
	}

	public void setChildren(List<Department> children) {
		this.children = children;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}
}
