package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the groupcategory database table.
 * 
 */
@Entity
@Table(name="groupcategory")
@NamedQuery(name="Groupcategory.findAll", query="SELECT g FROM Groupcategory g")
public class Groupcategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer groupcategoryid;

	@Column(nullable=false, length=128)
	private String groupcategoryname;

	public Groupcategory() {
	}

	public Integer getGroupcategoryid() {
		return this.groupcategoryid;
	}

	public void setGroupcategoryid(Integer groupcategoryid) {
		this.groupcategoryid = groupcategoryid;
	}

	public String getGroupcategoryname() {
		return this.groupcategoryname;
	}

	public void setGroupcategoryname(String groupcategoryname) {
		this.groupcategoryname = groupcategoryname;
	}

}