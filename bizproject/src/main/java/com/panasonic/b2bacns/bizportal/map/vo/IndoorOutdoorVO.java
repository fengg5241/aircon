package com.panasonic.b2bacns.bizportal.map.vo;

import java.util.List;

public class IndoorOutdoorVO {

	List<IndoorUnitVO> iduList;

	List<OutdoorUnitVO> oduList;

	/**
	 * @return the iduList
	 */
	public List<IndoorUnitVO> getIduList() {
		return iduList;
	}

	/**
	 * @param iduList
	 *            the iduList to set
	 */
	public void setIduList(List<IndoorUnitVO> iduList) {
		this.iduList = iduList;
	}

	/**
	 * @return the oduList
	 */
	public List<OutdoorUnitVO> getOduList() {
		return oduList;
	}

	/**
	 * @param oduList
	 *            the oduList to set
	 */
	public void setOduList(List<OutdoorUnitVO> oduList) {
		this.oduList = oduList;
	}

}
