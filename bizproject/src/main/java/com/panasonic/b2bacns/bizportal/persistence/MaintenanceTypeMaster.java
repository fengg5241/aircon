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
 * The persistent class for the maintenance_type_master database table.
 * 
 */
@Entity
@Table(name = "maintenance_type_master")
@NamedQuery(name = "MaintenanceTypeMaster.findAll", query = "SELECT m FROM MaintenanceTypeMaster m")
public class MaintenanceTypeMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(length = 256)
	private String description;

	// bi-directional many-to-one association to MaintenanceSetting
	@OneToMany(mappedBy = "maintenanceTypeMaster")
	private List<MaintenanceSetting> maintenanceSettings;

	public MaintenanceTypeMaster() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<MaintenanceSetting> getMaintenanceSettings() {
		return this.maintenanceSettings;
	}

	public void setMaintenanceSettings(
			List<MaintenanceSetting> maintenanceSettings) {
		this.maintenanceSettings = maintenanceSettings;
	}

	public MaintenanceSetting addMaintenanceSetting(
			MaintenanceSetting maintenanceSetting) {
		getMaintenanceSettings().add(maintenanceSetting);
		maintenanceSetting.setMaintenanceTypeMaster(this);

		return maintenanceSetting;
	}

	public MaintenanceSetting removeMaintenanceSetting(
			MaintenanceSetting maintenanceSetting) {
		getMaintenanceSettings().remove(maintenanceSetting);
		maintenanceSetting.setMaintenanceTypeMaster(null);

		return maintenanceSetting;
	}

}