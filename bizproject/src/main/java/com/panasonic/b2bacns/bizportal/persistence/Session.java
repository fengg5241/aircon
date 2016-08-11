package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the session database table.
 * 
 */
@Entity
@Table(name = "session")
@NamedQuery(name = "Session.findAll", query = "SELECT s FROM Session s")
public class Session implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(length = 45)
	private String createdby;

	@Column(nullable = false)
	private Timestamp creationdate;

	@Column(nullable = false, length = 45)
	private String email;

	@Column(nullable = false)
	private Byte status;

	@Column(length = 256)
	private String uniquesessionid;

	private Timestamp updatedate;

	@Column(length = 45)
	private String updatedby;

	@Column(length = 45)
	private String loginid;

	public Session() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Timestamp getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Timestamp creationdate) {
		this.creationdate = creationdate;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getUniquesessionid() {
		return this.uniquesessionid;
	}

	public void setUniquesessionid(String uniquesessionid) {
		this.uniquesessionid = uniquesessionid;
	}

	public Timestamp getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}

	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	/**
	 * @return the loginid
	 */
	public String getLoginid() {
		return loginid;
	}

	/**
	 * @param loginid
	 *            the loginid to set
	 */
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

}
