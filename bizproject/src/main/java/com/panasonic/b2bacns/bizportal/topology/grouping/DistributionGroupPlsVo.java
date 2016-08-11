package com.panasonic.b2bacns.bizportal.topology.grouping;

public class DistributionGroupPlsVo {
private int portnumber;
private String meterName;
	public String getMeterName() {
	return meterName;
}
public void setMeterName(String meterName) {
	this.meterName = meterName;
}
	private String  CaName;
	private String SiteName;	
	public int getPortnumber() {
		return portnumber;
	}
	public void setPortnumber(int portnumber) {
		this.portnumber = portnumber;
	}	
	
	
	public String getCaName() {
		return CaName;
	}
	public void setCaName(String caName) {
		CaName = caName;
	}
	public String getSiteName() {
		return SiteName;
	}
	public void setSiteName(String siteName) {
		SiteName = siteName;
	}
	
}
