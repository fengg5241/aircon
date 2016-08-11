package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the notificationsettings database table.
 * 
 */
@Entity
@Table(name = "notificationsettings")
@NamedQuery(name = "Notificationsetting.findAll", query = "SELECT n FROM Notificationsetting n")
public class Notificationsetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "NOTIFICATIONSETTINGS_ID_GENERATOR", sequenceName = "notificationsettings_id_seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "NOTIFICATIONSETTINGS_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;

	/* private Boolean isdel; */

	@Column(name = "on_off")
	private Boolean onOff;
	// bi-directional many-to-one association to Group
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private Group group;

	/*
	 * private Boolean ismaster;
	 */
	/*
	 * private Integer threshold;
	 * 
	 * //bi-directional many-to-one association to Company
	 * 
	 * @ManyToOne
	 * 
	 * @JoinColumn(name="company_id") private Company company;
	 */

	// bi-directional many-to-one association to NotificationtypeMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "notificationtype_id")
	private NotificationtypeMaster notificationtypeMaster;

	// bi-directional many-to-one association to UserNotificationSetting
	@OneToMany(fetch=FetchType.LAZY,mappedBy = "notificationsetting", cascade = { CascadeType.ALL },orphanRemoval=true)
	 private List<UserNotificationSetting> userNotificationSettings;

	public Notificationsetting() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*
	 * public Boolean getIsdel() { return this.isdel; }
	 * 
	 * public void setIsdel(Boolean isdel) { this.isdel = isdel; }
	 */

	public Boolean getOnOff() {
		return this.onOff;
	}

	public void setOnOff(Boolean onOff) {
		this.onOff = onOff;
	}

	/*
	 * public Integer getThreshold() { return this.threshold; }
	 * 
	 * public void setThreshold(Integer threshold) { this.threshold = threshold;
	 * }
	 * 
	 * public Company getCompany() { return this.company; }
	 * 
	 * public void setCompany(Company company) { this.company = company; }
	 */

	public NotificationtypeMaster getNotificationtypeMaster() {
		return this.notificationtypeMaster;
	}

	public void setNotificationtypeMaster(
			NotificationtypeMaster notificationtypeMaster) {
		this.notificationtypeMaster = notificationtypeMaster;
	}

	public List<UserNotificationSetting> getUserNotificationSettings() {
		return this.userNotificationSettings;
	}

	public void setUserNotificationSettings(
			List<UserNotificationSetting> userNotificationSettings) {
		this.userNotificationSettings.clear();
		this.userNotificationSettings.addAll(userNotificationSettings);
	}

	public UserNotificationSetting addUserNotificationSetting(
			UserNotificationSetting userNotificationSetting) {
		getUserNotificationSettings().add(userNotificationSetting);
		userNotificationSetting.setNotificationsetting(this);

		return userNotificationSetting;
	}

	public UserNotificationSetting removeUserNotificationSetting(
			UserNotificationSetting userNotificationSetting) {
		getUserNotificationSettings().remove(userNotificationSetting);
		userNotificationSetting.setNotificationsetting(null);

		return userNotificationSetting;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	/*
	 * public Boolean getIsmaster() { return this.ismaster; }
	 * 
	 * public void setIsmaster(Boolean ismaster) { this.ismaster = ismaster; }
	 */
}
