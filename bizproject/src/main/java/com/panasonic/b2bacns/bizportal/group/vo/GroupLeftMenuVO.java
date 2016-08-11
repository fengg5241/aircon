/**
 * 
 */
package com.panasonic.b2bacns.bizportal.group.vo;

import java.util.List;

/**
 * @author diksha.rattan
 * 
 */
public class GroupLeftMenuVO {

	private long groupId;

	private String groupName;

	private long companyId;

	private String companyName;

	private String groupCategory;

	private String groupTypeLevelName;

	private long groupTypeLevelID;

	private String svgPath;
	
	private String groupCriteria;
	
	private int level;

	private String text;
	
	private GroupLeftMenuLiAttrVO li_attr;
	
	private GroupLeftMenuStateVO state;

	private List<GroupLeftMenuVO> children;

	/**
	 * @return the groupId
	 */
	public long getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the companyId
	 */
	public long getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId
	 *            the companyId to set
	 */
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the groupTypeLevelName
	 */
	public String getGroupTypeLevelName() {
		return groupTypeLevelName;
	}

	/**
	 * @param groupTypeLevelName
	 *            the groupTypeLevelName to set
	 */
	public void setGroupTypeLevelName(String groupTypeLevelName) {
		this.groupTypeLevelName = groupTypeLevelName;
	}

	/**
	 * @return the groupTypeLevel
	 */
	public long getGroupTypeLevelID() {
		return groupTypeLevelID;
	}

	/**
	 * @param groupTypeLevel
	 *            the groupTypeLevel to set
	 */
	public void setGroupTypeLevelID(long groupTypeLevelID) {
		this.groupTypeLevelID = groupTypeLevelID;
	}

	/**
	 * @return the groups
	 */
	public List<GroupLeftMenuVO> getChildren() {
		return children;
	}

	/**
	 * @param groups
	 *            the groups to set
	 */
	public void setChildren(List<GroupLeftMenuVO> children) {
		this.children = children;
	}

	/**
	 * @return the groupCategory
	 */
	public String getGroupCategory() {
		return groupCategory;
	}

	/**
	 * @param groupCategory
	 *            the groupCategory to set
	 */
	public void setGroupCategory(String groupCategory) {
		this.groupCategory = groupCategory;
	}

	/*@Override
	public int compareTo(GroupLeftMenuVO o) {

		return this.getGroupName().compareTo(o.getGroupName());

	}*/

	/**
	 * @return the svgPath
	 */
	public String getSvgPath() {
		return svgPath;
	}

	/**
	 * @param svgPath the svgPath to set
	 */
	public void setSvgPath(String svgPath) {
		this.svgPath = svgPath;
	}

	/**
	 * @return the groupCriteria
	 */
	public String getGroupCriteria() {
		return groupCriteria;
	}

	/**
	 * @param groupCriteria the groupCriteria to set
	 */
	public void setGroupCriteria(String groupCriteria) {
		this.groupCriteria = groupCriteria;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the li_attr
	 */
	public GroupLeftMenuLiAttrVO getLi_attr() {
		return li_attr;
	}

	/**
	 * @param li_attr the li_attr to set
	 */
	public void setLi_attr(GroupLeftMenuLiAttrVO li_attr) {
		this.li_attr = li_attr;
	}

	/**
	 * @return the state
	 */
	public GroupLeftMenuStateVO getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(GroupLeftMenuStateVO state) {
		this.state = state;
	}

}
