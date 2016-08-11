package com.panasonic.b2bacns.bizportal.home.vo;

import java.io.Serializable;

/**
 * 
 * @author shobhit.singh
 *
 */
public class UserSelectionVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8670865069512735050L;
	public long entityId;
	public String entityType;
	public boolean selected;
	public boolean expanded;
	
	public long getEntityId() {
		return entityId;
	}
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
	
}
