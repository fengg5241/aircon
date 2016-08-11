package com.panasonic.b2bacns.bizportal.rc;

import java.util.List;

/**
 * @author fshan
 *
 */
public class RCOperationLogPaginationVO {
	/**
	 * @return the pageCount
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * @param pageCount the pageCount to set
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * @return the data
	 */
	public List<RCOperationLogVO> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<RCOperationLogVO> data) {
		this.data = data;
	}

	private int pageCount;
	
	private List<RCOperationLogVO> data;
}
