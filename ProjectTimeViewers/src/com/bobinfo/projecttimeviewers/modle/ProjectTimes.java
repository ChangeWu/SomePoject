package com.bobinfo.projecttimeviewers.modle;

import java.util.List;

public class ProjectTimes extends BaseInfo {
	public List<ProjectTime> projects;
	public static class ProjectTime{
		public String name = "";
		public String time ="";
		public String misstime ="";
		public String picpath = "";
	}
}
