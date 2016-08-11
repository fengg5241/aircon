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
 * The persistent class for the maintenance_setting database table.
 * 
 */
@Entity
@Table(name = "maintenance_setting")
@NamedQuery(name = "MaintenanceSetting.findAll", query = "SELECT m FROM MaintenanceSetting m")
public class MaintenanceSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MAINTENANCE_SETTING_ID_GENERATOR", sequenceName = "maintenance_setting_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MAINTENANCE_SETTING_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Long id;

	private Long threshold;

	// bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;

	// bi-directional many-to-one association to MaintenanceTypeMaster
	@ManyToOne
	@JoinColumn(name = "maintenance_type_id")
	private MaintenanceTypeMaster maintenanceTypeMaster;

	public MaintenanceSetting() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getThreshold() {
		return this.threshold;
	}

	public void setThreshold(Long threshold) {
		this.threshold = threshold;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public MaintenanceTypeMaster getMaintenanceTypeMaster() {
		return this.maintenanceTypeMaster;
	}

	public void setMaintenanceTypeMaster(
			MaintenanceTypeMaster maintenanceTypeMaster) {
		this.maintenanceTypeMaster = maintenanceTypeMaster;
	}

}