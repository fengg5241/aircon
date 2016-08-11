package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the rcoperation_master database table.
 * 
 */
@Entity
@Table(name = "rcoperation_master")
@NamedQuery(name = "RcoperationMaster.findAll", query = "SELECT r FROM RcoperationMaster r")
public class RcoperationMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String name;

	public RcoperationMaster() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
