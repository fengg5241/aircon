package com.panasonic.b2bacns.bizportal.map.vo;

import java.util.List;

public class CompanyMapVO {

	private Long companyId;

	private List<ParentGroupVO> children;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public List<ParentGroupVO> getChildren() {
		return children;
	}

	public void setChildren(List<ParentGroupVO> children) {
		this.children = children;
	}

}
