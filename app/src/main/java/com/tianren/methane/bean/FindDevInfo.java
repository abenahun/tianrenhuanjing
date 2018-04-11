package com.tianren.methane.bean;

public class FindDevInfo {

	private String devId;
	private String devPasswd;
	private String devDomain;
	
	public FindDevInfo(String devId, String devPasswd, String devDomain) {
		this.devId = devId;
		this.devPasswd = devPasswd;
		this.devDomain = devDomain;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getDevPasswd() {
		return devPasswd;
	}

	public void setDevPasswd(String devPasswd) {
		this.devPasswd = devPasswd;
	}
	
	public String getDevDomain() {
		return devDomain;
	}

	public void setDevDomain(String devDomain) {
		this.devDomain = devDomain;
	}
}
