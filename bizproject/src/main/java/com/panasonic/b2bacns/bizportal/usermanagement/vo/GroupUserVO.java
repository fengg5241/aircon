package com.panasonic.b2bacns.bizportal.usermanagement.vo;

import java.util.List;
import java.util.Map;

public class GroupUserVO {
	
	private long groupId;
    private String groupName;
    private long companyId;
    private String companyName;
    private String groupCategory;
    private String groupTypeLevelName;
    private long groupTypeLevelID;
    // new field added 
    private String svgpath;
    private String groupCriteria;
    private Integer level;
    private String text;
    private boolean enable = true;
    private Map<String,Object>  li_attr ;
    private Map<String,Object> state;  

    private List<GroupUserVO> children;
    
    

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getGroupCategory() {
		return groupCategory;
	}

	public void setGroupCategory(String groupCategory) {
		this.groupCategory = groupCategory;
	}

	public String getGroupTypeLevelName() {
		return groupTypeLevelName;
	}

	public void setGroupTypeLevelName(String groupTypeLevelName) {
		this.groupTypeLevelName = groupTypeLevelName;
	}

	public long getGroupTypeLevelID() {
		return groupTypeLevelID;
	}

	public void setGroupTypeLevelID(long groupTypeLevelID) {
		this.groupTypeLevelID = groupTypeLevelID;
	}

	public String getSvgpath() {
		return svgpath;
	}

	public void setSvgpath(String svgpath) {
		this.svgpath = svgpath;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Map<String, Object> getLi_attr() {
		return li_attr;
	}

	public void setLi_attr(Map<String, Object> li_attr) {
		this.li_attr = li_attr;
	}

	public Map<String, Object> getState() {
		return state;
	}

	public void setState(Map<String, Object> state) {
		this.state = state;
	}

	public List<GroupUserVO> getChildren() {
		return children;
	}

	public void setChildren(List<GroupUserVO> children) {
		this.children = children;
	}

	public String getGroupCriteria() {
		return groupCriteria;
	}

	public void setGroupCriteria(String groupCriteria) {
		this.groupCriteria = groupCriteria;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
    
    
    

}
