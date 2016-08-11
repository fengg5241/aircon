package com.panasonic.b2bacns.bizportal.topology.grouping;

public class DistributionGroupIduVo {
	private String s_linkaddress;	
	
	private String deviceName;	
	
	
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getS_linkaddress() {
		return s_linkaddress;
	}
	public void setS_linkaddress(String s_linkaddress) {
		this.s_linkaddress = s_linkaddress;
	}
	public String getCaName() {
		return CaName;
	}
	public void setCaName(String caName) {
		CaName = caName;
	}
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	private String CaName;
	
	private String siteName;
}
