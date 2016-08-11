package com.panasonic.b2bacns.bizportal.common;

import java.io.Serializable;

public class PermissionVO implements Serializable {

	private static final long serialVersionUID = 5183142482000608931L;

	private Long permissionID;

	private String permissionName;

	private String permissionUrl;

	public Long getPermissionID() {
		return permissionID;
	}

	public void setPermissionID(Long permissionID) {
		this.permissionID = permissionID;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionUrl() {
		return permissionUrl;
	}

	public void setPermissionUrl(String permissionUrl) {
		this.permissionUrl = permissionUrl;
	}

}
