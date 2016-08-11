package com.panasonic.b2bacns.bizportal.acconfig.vo;

import java.math.BigInteger;

/**
 * Cloud Adapters
 * 
 * @author jwchan
 * 
 */
public class CAConfigVO {

	private String company_name;
	private String site_name;
	private String address;
	private String mov1pulse_type;
	private String mov1pulse_id;
	private String mov1pulse_factor;
	private String mov1pulse;
	private String mov2pulse_type;
	private String mov2pulse_id;
	private String mov2pulse_factor;
	
	private String mov2pulse;
	private String mov4pulse_type;
	private String mov4pulse_id;
	private String mov4pulse_factor;
	private String mov4pulse;
	private BigInteger id;
	private BigInteger group_id;
	
	private String alarm_code;
	
	//add by shanf
	private String deviceModel;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getAlarm_code() {
		return alarm_code;
	}
	public void setAlarm_code(String alarm_code) {
		this.alarm_code = alarm_code;
	}
	public String getMov1pulse_type() {
		return mov1pulse_type;
	}
	public void setMov1pulse_type(String mov1pulse_type) {
		this.mov1pulse_type = mov1pulse_type;
	}
	public String getMov1pulse_id() {
		return mov1pulse_id;
	}
	public void setMov1pulse_id(String mov1pulse_id) {
		this.mov1pulse_id = mov1pulse_id;
	}
	public String getMov1pulse_factor() {
		return mov1pulse_factor;
	}
	public void setMov1pulse_factor(String mov1pulse_factor) {
		this.mov1pulse_factor = mov1pulse_factor;
	}
	public String getMov2pulse_type() {
		return mov2pulse_type;
	}
	public void setMov2pulse_type(String mov2pulse_type) {
		this.mov2pulse_type = mov2pulse_type;
	}
	public String getMov2pulse_id() {
		return mov2pulse_id;
	}
	public void setMov2pulse_id(String mov2pulse_id) {
		this.mov2pulse_id = mov2pulse_id;
	}
	public String getMov2pulse_factor() {
		return mov2pulse_factor;
	}
	public void setMov2pulse_factor(String mov2pulse_factor) {
		this.mov2pulse_factor = mov2pulse_factor;
	}
	public String getMov4pulse_type() {
		return mov4pulse_type;
	}
	public void setMov4pulse_type(String mov4pulse_type) {
		this.mov4pulse_type = mov4pulse_type;
	}
	public String getMov4pulse_id() {
		return mov4pulse_id;
	}
	public void setMov4pulse_id(String mov4pulse_id) {
		this.mov4pulse_id = mov4pulse_id;
	}
	public String getMov4pulse_factor() {
		return mov4pulse_factor;
	}
	public void setMov4pulse_factor(String mov4pulse_factor) {
		this.mov4pulse_factor = mov4pulse_factor;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	
	public String getCustomerName() {
		return company_name;
	}
	public void setCustomerName(String company_name) {
		this.company_name = company_name;
	}
	
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMov1pulse() {
		return mov1pulse;
	}
	public void setMov1pulse(String mov1pulse) {
		this.mov1pulse = mov1pulse;
	}
	public String getMov2pulse() {
		return mov2pulse;
	}
	public void setMov2pulse(String mov2pulse) {
		this.mov2pulse = mov2pulse;
	}
	public String getMov4pulse() {
		return mov4pulse;
	}
	public void setMov4pulse(String mov4pulse) {
		this.mov4pulse = mov4pulse;
	}
	public BigInteger getGroup_id() {
		return group_id;
	}
	public void setGroup_id(BigInteger group_id) {
		this.group_id = group_id;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	

}
