package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the timezonemaster database table.
 * 
 */
@Entity
@Table(name = "timezonemaster")
@NamedQuery(name = "Timezonemaster.findAll", query = "SELECT t FROM Timezonemaster t")
public class Timezonemaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(length = 255)
	private String timezone;

/*	// bi-directional many-to-one association to User
	@OneToMany(mappedBy = "timezonemaster")
	private List<User> users;
*/
	// bi-directional many-to-one association to Group
	@OneToMany(mappedBy = "timezonemaster")
	private List<Group> groups;

	// bi-directional many-to-one association to Adapter
	@OneToMany(mappedBy = "timezonemaster")
	private List<Adapter> adapters;

	public Timezonemaster() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTimezone() {
		return this.timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/*public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
*/
/*	public User addUser(User user) {
		getUsers().add(user);
		user.setTimezonemaster(this);

		return user;
	}
*/
	/*public User removeUser(User user) {
		getUsers().remove(user);
		user.setTimezonemaster(null);

		return user;
	}
*/
	public List<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Group addGroup(Group group) {
		getGroups().add(group);
		group.setTimezonemaster(this);

		return group;
	}

	public Group removeGroup(Group group) {
		getGroups().remove(group);
		group.setTimezonemaster(null);

		return group;
	}

	/**
	 * @return the adapters
	 */
	public List<Adapter> getAdapters() {
		return adapters;
	}

	/**
	 * @param adapters
	 *            the adapters to set
	 */
	public void setAdapters(List<Adapter> adapters) {
		this.adapters = adapters;
	}

}
