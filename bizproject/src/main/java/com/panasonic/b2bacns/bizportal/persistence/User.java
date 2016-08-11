package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @SequenceGenerator(name = "USERS_ID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(length = 255)
	private String confirmationtoken;

	@Column(name = "confirmed_date")
	private Date confirmedDate;

	@Column(name = "confirmedsent_date")
	private Date confirmedsentDate;

	@Column(length = 255)
	private String country;

	/*
	 * public Integer getTimezoneId() { return timezoneId; }
	 * 
	 * public void setTimezoneId(Integer timezoneId) { this.timezoneId =
	 * timezoneId; }
	 */

	@Column(length = 255)
	private String createdby;

	private Date creationdate;

	@Column(length = 8)
	private String department;

	@Column(length = 255)
	private String email;

	@Column(length = 255)
	private String encryptedpassword;

	@Column(nullable = false)
	private Integer failedattempt;

	@Column(length = 255)
	private String firstname;

	@Column(length = 255)
	private String forgottoken;

	private Boolean isdel;

	@Column(name = "isEmailVerified", nullable = false)
	private byte isverified;

	@Column(name = "lastactivity_date")
	private Date lastactivityDate;

	@Column(length = 255)
	private String lastname;

	@Column(name = "locked_date")
	private Date lockedDate;

	@Column(name = "passwordchange_date")
	private Date passwordchangeDate;

	@Column(name = "rembembercreated_date")
	private Date rembembercreatedDate;

	@Column(name = "resetpassword_sent_date")
	private Date resetpasswordSentDate;

	@Column(length = 255)
	private String resetpasswordtoken;

	@Column(length = 255)
	private String resettoken;

	@Column(length = 16)
	private String telephone;

	@Column(length = 255)
	private String unlocktoken;

	private Date updatedate;

	@Column(length = 255)
	private String updatedby;

	@Column
	private String lastVisitedGroups;

	@Column(length = 255)
	private String loginid;

	@Column(name = "is_locked")
	private boolean isLocked;

	@Column(name = "is_valid")
	private boolean isValid;

	@Column(name = "lastlogin_date")
	private Date lastLoginDate;

	/*
	 * @Column(name = "timezone_id") private Integer timezoneId;
	 */

	@Column(name = "companyid")
	private Long companyId;

	@Column(name = "roles_id")
	private Long rolesId;
	// bi-directional many-to-one association to Companiesuser
	@OneToMany(mappedBy = "user")
	private Set<Companiesuser> companiesusers;

	// bi-directional many-to-one association to RcuserAction
	@OneToMany(mappedBy = "user")
	private Set<RcuserAction> rcuserActions;

	// bi-directional many-to-one association to UserNotificationSetting
	@OneToMany(mappedBy = "user")
	private Set<UserNotificationSetting> userNotificationSettings;

	// bi-directional many-to-one association to Useraudit
	@OneToMany(mappedBy = "user")
	private Set<Useraudit> useraudits;

	// bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(insertable = false, updatable = false, name = "roles_id")
	private Role role;

	// bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(insertable = false, updatable = false, name = "companyid")
	private Company company;

	/*
	 * // bi-directional many-to-one association to Timezonemaster
	 * 
	 * @ManyToOne
	 * 
	 * @JoinColumn(insertable = false, updatable = false, name = "timezone_id")
	 * private Timezonemaster timezonemaster;
	 */
	public User() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConfirmationtoken() {
		return this.confirmationtoken;
	}

	public void setConfirmationtoken(String confirmationtoken) {
		this.confirmationtoken = confirmationtoken;
	}

	public Date getConfirmedDate() {
		return this.confirmedDate;
	}

	public void setConfirmedDate(Date confirmedDate) {
		this.confirmedDate = confirmedDate;
	}

	public Date getConfirmedsentDate() {
		return this.confirmedsentDate;
	}

	public void setConfirmedsentDate(Date confirmedsentDate) {
		this.confirmedsentDate = confirmedsentDate;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Date getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryptedpassword() {
		return this.encryptedpassword;
	}

	public void setEncryptedpassword(String encryptedpassword) {
		this.encryptedpassword = encryptedpassword;
	}

	public Integer getFailedattempt() {
		return this.failedattempt;
	}

	public void setFailedattempt(Integer failedattempt) {
		this.failedattempt = failedattempt;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getForgottoken() {
		return this.forgottoken;
	}

	public void setForgottoken(String forgottoken) {
		this.forgottoken = forgottoken;
	}

	public Boolean getIsdel() {
		return this.isdel;
	}

	public void setIsdel(Boolean isdel) {
		this.isdel = isdel;
	}

	public byte getIsverified() {
		return this.isverified;
	}

	public void setIsverified(byte isverified) {
		this.isverified = isverified;
	}

	public Date getLastactivityDate() {
		return this.lastactivityDate;
	}

	public void setLastactivityDate(Date lastactivityDate) {
		this.lastactivityDate = lastactivityDate;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getLockedDate() {
		return this.lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public Date getPasswordchangeDate() {
		return this.passwordchangeDate;
	}

	public void setPasswordchangeDate(Date passwordchangeDate) {
		this.passwordchangeDate = passwordchangeDate;
	}

	public Date getRembembercreatedDate() {
		return this.rembembercreatedDate;
	}

	public void setRembembercreatedDate(Date rembembercreatedDate) {
		this.rembembercreatedDate = rembembercreatedDate;
	}

	public Date getResetpasswordSentDate() {
		return this.resetpasswordSentDate;
	}

	public void setResetpasswordSentDate(Date resetpasswordSentDate) {
		this.resetpasswordSentDate = resetpasswordSentDate;
	}

	public String getResetpasswordtoken() {
		return this.resetpasswordtoken;
	}

	public void setResetpasswordtoken(String resetpasswordtoken) {
		this.resetpasswordtoken = resetpasswordtoken;
	}

	public String getResettoken() {
		return this.resettoken;
	}

	public void setResettoken(String resettoken) {
		this.resettoken = resettoken;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUnlocktoken() {
		return this.unlocktoken;
	}

	public void setUnlocktoken(String unlocktoken) {
		this.unlocktoken = unlocktoken;
	}

	public Date getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public Set<Companiesuser> getCompaniesusers() {
		return this.companiesusers;
	}

	public void setCompaniesusers(Set<Companiesuser> companiesusers) {
		this.companiesusers = companiesusers;
	}

	public Companiesuser addCompaniesuser(Companiesuser companiesuser) {
		getCompaniesusers().add(companiesuser);
		companiesuser.setUser(this);

		return companiesuser;
	}

	public Companiesuser removeCompaniesuser(Companiesuser companiesuser) {
		getCompaniesusers().remove(companiesuser);
		companiesuser.setUser(null);

		return companiesuser;
	}

	public Set<RcuserAction> getRcuserActions() {
		return this.rcuserActions;
	}

	public void setRcuserActions(Set<RcuserAction> rcuserActions) {
		this.rcuserActions = rcuserActions;
	}

	public RcuserAction addRcuserAction(RcuserAction rcuserAction) {
		getRcuserActions().add(rcuserAction);
		rcuserAction.setUser(this);

		return rcuserAction;
	}

	public RcuserAction removeRcuserAction(RcuserAction rcuserAction) {
		getRcuserActions().remove(rcuserAction);
		rcuserAction.setUser(null);

		return rcuserAction;
	}

	public Set<UserNotificationSetting> getUserNotificationSettings() {
		return this.userNotificationSettings;
	}

	public void setUserNotificationSettings(
			Set<UserNotificationSetting> userNotificationSettings) {
		this.userNotificationSettings = userNotificationSettings;
	}

	public UserNotificationSetting addUserNotificationSetting(
			UserNotificationSetting userNotificationSetting) {
		getUserNotificationSettings().add(userNotificationSetting);
		userNotificationSetting.setUser(this);

		return userNotificationSetting;
	}

	public UserNotificationSetting removeUserNotificationSetting(
			UserNotificationSetting userNotificationSetting) {
		getUserNotificationSettings().remove(userNotificationSetting);
		userNotificationSetting.setUser(null);

		return userNotificationSetting;
	}

	public Set<Useraudit> getUseraudits() {
		return this.useraudits;
	}

	public void setUseraudits(Set<Useraudit> useraudits) {
		this.useraudits = useraudits;
	}

	public Useraudit addUseraudit(Useraudit useraudit) {
		getUseraudits().add(useraudit);
		useraudit.setUser(this);

		return useraudit;
	}

	public Useraudit removeUseraudit(Useraudit useraudit) {
		getUseraudits().remove(useraudit);
		useraudit.setUser(null);

		return useraudit;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	/*
	 * public Timezonemaster getTimezonemaster() { return this.timezonemaster; }
	 * 
	 * public void setTimezonemaster(Timezonemaster timezonemaster) {
	 * this.timezonemaster = timezonemaster; }
	 */
	public String getLastVisitedGroups() {
		return lastVisitedGroups;
	}

	public void setLastVisitedGroups(String lastVisitedGroups) {
		this.lastVisitedGroups = lastVisitedGroups;
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

	/**
	 * @return the isLocked
	 */
	public boolean isLocked() {
		return isLocked;
	}

	/**
	 * @param isLocked
	 *            the isLocked to set
	 */
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * @return the isValid
	 */
	public boolean isValid() {
		return isValid;
	}

	/**
	 * @param isValid
	 *            the isValid to set
	 */
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	/**
	 * @return the lastLoginDate
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param lastLoginDate
	 *            the lastLoginDate to set
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Long getRolesId() {
		return rolesId;
	}

	public void setRolesId(Long rolesId) {
		this.rolesId = rolesId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
