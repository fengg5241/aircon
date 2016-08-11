package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the errorcode_master database table.
 * 
 */
@Entity
@Table(name = "errorcode_master")
@NamedQuery(name = "ErrorcodeMaster.findAll", query = "SELECT e FROM ErrorcodeMaster e")
public class ErrorcodeMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 64)
	private String code;
	
	// Start: Modified by ravi
	@Column(nullable = false, length = 64)
	private String error_code;
	
	@Column(nullable = false, length = 16)
	private String device_type;
	// End: Modified by Ravi
	
	@Column(name = "countermeasure_2way", length = 255)
	private String countermeasure2way;

	@Column(name = "countermeasure_3way", length = 255)
	private String countermeasure3way;

	@Column(name = "countermeasure_customer", length = 255)
	private String countermeasureCustomer;

	private Integer createdby;

	private Timestamp creationdate;

	@Column(name = "customer_description", length = 128)
	private String customerDescription;

	@Column(name = "maintenance_description", length = 128)
	private String maintenanceDescription;

	@Column(length = 16)
	private String severity;

	@Column(length = 45)
	private String type;

	private Timestamp updatedate;

	private Integer updatedby;

	// bi-directional many-to-one association to NotificationtypeMaster
	@ManyToOne
	@JoinColumn(name = "notificationtype_id")
	private NotificationtypeMaster notificationtypeMaster;

	public ErrorcodeMaster() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	// Added by Ravi
	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	// End of adding by ravi
	
	public Integer getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Timestamp getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Timestamp creationdate) {
		this.creationdate = creationdate;
	}

	public String getCustomerDescription() {
		return this.customerDescription;
	}

	public void setCustomerDescription(String customerDescription) {
		this.customerDescription = customerDescription;
	}

	public String getMaintenanceDescription() {
		return this.maintenanceDescription;
	}

	public void setMaintenanceDescription(String maintenanceDescription) {
		this.maintenanceDescription = maintenanceDescription;
	}

	public String getSeverity() {
		return this.severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public Timestamp getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}

	public Integer getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(Integer updatedby) {
		this.updatedby = updatedby;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getCountermeasure2way() {
		return this.countermeasure2way;
	}

	public void setCountermeasure2way(String countermeasure2way) {
		this.countermeasure2way = countermeasure2way;
	}

	public String getCountermeasure3way() {
		return this.countermeasure3way;
	}

	public void setCountermeasure3way(String countermeasure3way) {
		this.countermeasure3way = countermeasure3way;
	}

	public String getCountermeasureCustomer() {
		return this.countermeasureCustomer;
	}

	public void setCountermeasureCustomer(String countermeasureCustomer) {
		this.countermeasureCustomer = countermeasureCustomer;
	}

	public NotificationtypeMaster getNotificationtypeMaster() {
		return this.notificationtypeMaster;
	}

	public void setNotificationtypeMaster(
			NotificationtypeMaster notificationtypeMaster) {
		this.notificationtypeMaster = notificationtypeMaster;
	}
}
