package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the group_level database table.
 * 
 */
@Entity
@Table(name="group_level")
@NamedQuery(name="GroupLevel.findAll", query="SELECT g FROM GroupLevel g")
public class GroupLevel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="type_level", length=128)
	private String typeLevel;

	@Column(name="type_level_name", length=128)
	private String typeLevelName;

	//bi-directional many-to-one association to Group
	@OneToMany(mappedBy="groupLevel")
	private List<Group> groups;

	public GroupLevel() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeLevel() {
		return this.typeLevel;
	}

	public void setTypeLevel(String typeLevel) {
		this.typeLevel = typeLevel;
	}

	public String getTypeLevelName() {
		return this.typeLevelName;
	}

	public void setTypeLevelName(String typeLevelName) {
		this.typeLevelName = typeLevelName;
	}

	public List<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Group addGroup(Group group) {
		getGroups().add(group);
		group.setGroupLevel(this);

		return group;
	}

	public Group removeGroup(Group group) {
		getGroups().remove(group);
		group.setGroupLevel(null);

		return group;
	}

}