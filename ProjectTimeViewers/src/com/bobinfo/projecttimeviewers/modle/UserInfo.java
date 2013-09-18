package com.bobinfo.projecttimeviewers.modle;

public class UserInfo extends BaseInfo{
	public String userName;
	public String password;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserInfo [userName=" + userName + ", password=" + password
				+ ", state=" + state + ", msg=" + msg + "]";
	}
	
}
