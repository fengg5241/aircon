package com.panasonic.b2bacns.bizportal.topology.grouping;

public class DistributionGroupIduData {

	public DistributionGroupIduData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DistributionGroupIduData(String id, int dist_grp, String device,
			String facilityId) {
		super();
		this.id = id;
		this.dist_grp = dist_grp;
		this.device = device;
		this.facilityId = facilityId;
	}

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private String id;
	private int dist_grp;
	private String device;
	private String facilityId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDist_grp() {
		return dist_grp;
	}

	public void setDist_grp(int dist_grp) {
		this.dist_grp = dist_grp;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

}
