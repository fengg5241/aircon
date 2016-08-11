/**
 * 
 */
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
import javax.persistence.Table;

/**
 * @author ravi
 * The persistent class for the pulse_meter database table.
 */
@Entity
@Table(name = "pulse_meter")
@NamedQuery(name = "pulse_meter.findAll", query = "SELECT i FROM Pulsemeter i")
public class Pulsemeter implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	@Column(length = 225)
	private String  meter_name;
	
	private int  port_number;
	
	//private int  distribution_group_id;
	  
	@Column(length = 250)
	private String oid;
	
	@ManyToOne
	@JoinColumn(name = "adapters_id")
	private Adapter adapter;
	
	@Column(length = 225)
	private String device_model;
	
	@Column(length = 45)
	private String meter_type;
	
	@Column(length = 250)
	private String multi_factor;
	
	@Column(length = 250)
	private String creationdate;
	
	@Column(length = 250)
	private String connection_type;
	
	/**
	 * 
	 */
	public Pulsemeter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the meter_name
	 */
	public String getMeter_name() {
		return meter_name;
	}

	/**
	 * @param meter_name the meter_name to set
	 */
	public void setMeter_name(String meter_name) {
		this.meter_name = meter_name;
	}

	/**
	 * @return the port_number
	 */
	public int getPort_number() {
		return port_number;
	}

	/**
	 * @param port_number the port_number to set
	 */
	public void setPort_number(int port_number) {
		this.port_number = port_number;
	}

	/**
	 * @return the distribution_group_id
	 */
	/*public int getDistribution_group_id() {
		return distribution_group_id;
	}*/

	/**
	 * @param distribution_group_id the distribution_group_id to set
	 */
	/*public void setDistribution_group_id(int distribution_group_id) {
		this.distribution_group_id = distribution_group_id;
	}*/

	/**
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @param oid the oid to set
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	/**
	 * @return the adapter
	 */
	public Adapter getAdapter() {
		return adapter;
	}

	/**
	 * @param adapter the adapter to set
	 */
	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
	}

	/**
	 * @return the device_model
	 */
	public String getDevice_model() {
		return device_model;
	}

	/**
	 * @param device_model the device_model to set
	 */
	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}

	/**
	 * @return the meter_type
	 */
	public String getMeter_type() {
		return meter_type;
	}

	/**
	 * @param meter_type the meter_type to set
	 */
	public void setMeter_type(String meter_type) {
		this.meter_type = meter_type;
	}

	/**
	 * @return the multi_factor
	 */
	public String getMulti_factor() {
		return multi_factor;
	}

	/**
	 * @param multi_factor the multi_factor to set
	 */
	public void setMulti_factor(String multi_factor) {
		this.multi_factor = multi_factor;
	}

	/**
	 * @return the creationdate
	 */
	public String getCreationdate() {
		return creationdate;
	}

	/**
	 * @param creationdate the creationdate to set
	 */
	public void setCreationdate(String creationdate) {
		this.creationdate = creationdate;
	}

	/**
	 * @return the connection_type
	 */
	public String getConnection_type() {
		return connection_type;
	}

	/**
	 * @param connection_type the connection_type to set
	 */
	public void setConnection_type(String connection_type) {
		this.connection_type = connection_type;
	}

}
