package com.panasonic.b2bacns.bizportal.topology.grouping;

public class DistributionGroupPlsData {
	
	private int pdu_id;
	private int dist_grp;
	
	
	
	public int getPdu_id() {
		return pdu_id;
	}
	public void setPdu_id(int pdu_id) {
		this.pdu_id = pdu_id;
	}
	
	

	
	public DistributionGroupPlsData(int pdu_id, int dist_grp) {
		super();
		this.pdu_id = pdu_id;
		this.dist_grp = dist_grp;
	}
	public int getDist_grp() {
		return dist_grp;
	}
	public void setDist_grp(int dist_grp) {
		this.dist_grp = dist_grp;
	}
	public DistributionGroupPlsData() {
		super();
	}
}
