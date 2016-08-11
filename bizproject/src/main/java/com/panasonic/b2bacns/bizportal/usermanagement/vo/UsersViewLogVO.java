package com.panasonic.b2bacns.bizportal.usermanagement.vo;

public class UsersViewLogVO {
	private String date_time;
	private String update_by;

	private String update_data;
	private Long userid;

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public String getUpdate_data() {
		return update_data;
	}

	public void setUpdate_data(String update_data) {
		this.update_data = update_data;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

}
