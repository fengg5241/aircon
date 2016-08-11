/**
 * 
 */
package com.panasonic.b2bacns.bizportal.rcset.vo;

import java.util.Map;
import java.util.Set;

/**
 * @author Narendra.Kumar
 * 
 */
public class RCSetControlRequestVO {
	private Set<Long> id;
	private Map<String, String> operation;

	/**
	 * @return the id
	 */
	public Set<Long> getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Set<Long> id) {
		this.id = id;
	}

	/**
	 * @return the operation
	 */
	public Map<String, String> getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(Map<String, String> operation) {
		this.operation = operation;
	}

}
