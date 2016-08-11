package com.panasonic.b2bacns.bizportal.acconfig.vo;

import java.util.Set;

public class ACConfigIDUVO {

	Set<ACConfigVO> iduList;
	Set<RefrigerantSVG> refrigerantList;

	/**
	 * @return the iduList
	 */
	public Set<ACConfigVO> getIduList() {
		return iduList;
	}

	/**
	 * @param iduList
	 *            the iduList to set
	 */
	public void setIduList(Set<ACConfigVO> iduList) {
		this.iduList = iduList;
	}

	/**
	 * @return the refrigerantList
	 */
	public Set<RefrigerantSVG> getRefrigerantList() {
		return refrigerantList;
	}

	/**
	 * @param refrigerantList
	 *            the refrigerantList to set
	 */
	public void setRefrigerantList(Set<RefrigerantSVG> refrigerantList) {
		this.refrigerantList = refrigerantList;
	}

}
