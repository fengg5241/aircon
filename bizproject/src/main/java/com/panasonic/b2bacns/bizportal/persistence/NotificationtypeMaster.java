package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the notificationtype_master database table.
 * 
 */
@Entity
@Table(name = "notificationtype_master")
@NamedQuery(name = "NotificationtypeMaster.findAll", query = "SELECT n FROM NotificationtypeMaster n")
public class NotificationtypeMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "NOTIFICATIONTYPE_MASTER_ID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTIFICATIONTYPE_MASTER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(length = 32)
	private String typename;

	// bi-directional many-to-one association to Notificationsetting
	@OneToMany(mappedBy = "notificationtypeMaster")
	private Set<Notificationsetting> notificationsettings;

	public NotificationtypeMaster() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public Set<Notificationsetting> getNotificationsettings() {
		return this.notificationsettings;
	}

	public void setNotificationsettings(
			Set<Notificationsetting> notificationsettings) {
		this.notificationsettings = notificationsettings;
	}

	public Notificationsetting addNotificationsetting(
			Notificationsetting notificationsetting) {
		getNotificationsettings().add(notificationsetting);
		notificationsetting.setNotificationtypeMaster(this);

		return notificationsetting;
	}

	public Notificationsetting removeNotificationsetting(
			Notificationsetting notificationsetting) {
		getNotificationsettings().remove(notificationsetting);
		notificationsetting.setNotificationtypeMaster(null);

		return notificationsetting;
	}

}