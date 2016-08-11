package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the user_notification_settings database table.
 * 
 */
@Entity
@Table(name = "user_notification_settings")
@NamedQuery(name = "UserNotificationSetting.findAll", query = "SELECT u FROM UserNotificationSetting u")
public class UserNotificationSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "user_notification_settings_id_seq", sequenceName = "user_notification_settings_id_seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "user_notification_settings_id_seq")
	@Column(unique = true, nullable = false)
	private Integer id;

	/*
	 * @Column(length = 10) private String notificationby;
	 */

	// bi-directional many-to-one association to Notificationsetting
	@ManyToOne
	@JoinColumn(name = "notification_settings_id")
	private Notificationsetting notificationsetting;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "email_status")
	private Boolean emailStatus;

	public UserNotificationSetting() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*
	 * public String getNotificationby() { return this.notificationby; }
	 * 
	 * public void setNotificationby(String notificationby) {
	 * this.notificationby = notificationby; }
	 */

	public Notificationsetting getNotificationsetting() {
		return this.notificationsetting;
	}

	public void setNotificationsetting(Notificationsetting notificationsetting) {
		this.notificationsetting = notificationsetting;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(Boolean emailStatus) {
		this.emailStatus = emailStatus;
	}

}
