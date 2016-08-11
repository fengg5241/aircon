package com.panasonic.b2bacns.bizportal.topology.grouping;

import java.io.Serializable;
import java.util.List;

public class OduTopology implements Serializable {
	 
	/*
	 * 
	 * @author pramod
	 * 
	 */
	 
	private static final long serialVersionUID = 1L;
	private	List<OduData> topologyList;

	public List<OduData> getTopologyList() {
		return topologyList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setTopologyList(List<OduData> topologyList) {
		this.topologyList = topologyList;
	}
}
