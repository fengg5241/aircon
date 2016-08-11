package com.panasonic.b2bacns.bizportal.topology.grouping;

import java.util.List;

public class IduTopology {

	/*
	 * 
	 * @author pramod
	 * 
	 */
	public List<IduData> getTopologyList() {
		return topologyList;
	}

	public void setTopologyList(List<IduData> topologyList) {
		this.topologyList = topologyList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private	List<IduData> topologyList;
}
