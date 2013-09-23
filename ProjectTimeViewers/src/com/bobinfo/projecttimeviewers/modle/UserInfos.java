package com.bobinfo.projecttimeviewers.modle;

public class UserInfos extends BaseInfo{
	public UserInfo userInfo = new UserInfo();
	public static class UserInfo{
		public int id;
		public String userName = "";
		public String password = "";
		public int role ;
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
		
		public int getRole() {
			return role;
		}
		public void setRole(int role) {
			this.role = role;
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		@Override
		public String toString() {
			return "UserInfo [id=" + id + ", userName=" + userName
					+ ", password=" + password + ", role=" + role + "]";
		}
		
	}
}
