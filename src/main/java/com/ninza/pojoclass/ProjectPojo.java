package com.ninza.pojoclass;

public class ProjectPojo {
	String projectName;
	String Status;
	String CreatedBy;
	int teamSize;
	public ProjectPojo() {
		
	}
	public ProjectPojo(String projectName, String status, String createdBy, int teamSize) {
		super();
		this.projectName = projectName;
		this.Status = status;
		this.CreatedBy = createdBy;
		this.teamSize = teamSize;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}
	public int getTeamSize() {
		return teamSize;
	}
	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}
	
	

}
