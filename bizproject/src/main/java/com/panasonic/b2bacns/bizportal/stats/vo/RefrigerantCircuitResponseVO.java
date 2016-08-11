/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.vo;

import java.util.List;

/**
 * @author diksha.rattan
 * 
 */
public class RefrigerantCircuitResponseVO {

	List<RefrigerantCircuitVO> refrigerantCircuits;

	/**
	 * @return the refrigerantCircuits
	 */
	public List<RefrigerantCircuitVO> getRefrigerantCircuits() {
		return refrigerantCircuits;
	}

	/**
	 * @param refrigerantCircuits
	 *            the refrigerantCircuits to set
	 */
	public void setRefrigerantCircuits(
			List<RefrigerantCircuitVO> refrigerantCircuits) {
		this.refrigerantCircuits = refrigerantCircuits;
	}

}
