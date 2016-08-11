package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the groupscriteria database table.
 * 
 */
@Entity
@Table(name="groupscriteria")
@NamedQuery(name="Groupscriteria.findAll", query="SELECT g FROM Groupscriteria g")
public class Groupscriteria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, length=255)
	private String id;

	@Column(name="is_child")
	private Integer isChild;

	@Column(name="is_sibling")
	private Integer isSibling;

	@Column(name="is_unit")
	private Integer isUnit;

	//bi-directional many-to-one association to Group
	@OneToMany(mappedBy="groupscriteria")
	private List<Group> groups;

	public Groupscriteria() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIsChild() {
		return this.isChild;
	}

	public void setIsChild(Integer isChild) {
		this.isChild = isChild;
	}

	public Integer getIsSibling() {
		return this.isSibling;
	}

	public void setIsSibling(Integer isSibling) {
		this.isSibling = isSibling;
	}

	public Integer getIsUnit() {
		return this.isUnit;
	}

	public void setIsUnit(Integer isUnit) {
		this.isUnit = isUnit;
	}

	public List<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Group addGroup(Group group) {
		getGroups().add(group);
		group.setGroupscriteria(this);

		return group;
	}

	public Group removeGroup(Group group) {
		getGroups().remove(group);
		group.setGroupscriteria(null);

		return group;
	}

}