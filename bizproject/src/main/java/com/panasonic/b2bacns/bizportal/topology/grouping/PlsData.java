package com.panasonic.b2bacns.bizportal.topology.grouping;

import java.io.Serializable;
import java.util.List;

public class PlsData implements Serializable{
	

	/*
	 * 
	 * @author pramod
	 * 
	 */
public List<Topology> getTopologyList() {
		return topologyList;
	}

	public void setTopologyList(List<Topology> topologyList) {
		this.topologyList = topologyList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private	List<Topology> topologyList;

}
