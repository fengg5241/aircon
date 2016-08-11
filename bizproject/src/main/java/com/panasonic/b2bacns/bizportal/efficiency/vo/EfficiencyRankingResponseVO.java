/**
 * 
 */
package com.panasonic.b2bacns.bizportal.efficiency.vo;

import java.util.List;

/**
 * @author Narendra.Kumar
 * 
 */
public class EfficiencyRankingResponseVO {

	// {[compare_by:{block1, block2,..},value:{20,10,..}]}// order by value high
	// on top, value and compare_by one to one mapped.
	private List<String> compare_by;
	private List<Double> value;

	/**
	 * @return the compare_by
	 */
	public List<String> getCompare_by() {
		return compare_by;
	}

	/**
	 * @param compare_by
	 *            the compare_by to set
	 */
	public void setCompare_by(List<String> compare_by) {
		this.compare_by = compare_by;
	}

	/**
	 * @return the value
	 */
	public List<Double> getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(List<Double> value) {
		this.value = value;
	}

}
