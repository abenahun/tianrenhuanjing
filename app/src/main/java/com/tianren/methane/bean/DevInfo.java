package com.tianren.methane.bean;

import java.io.Serializable;

public class DevInfo implements Serializable {
	public static final int TYPE_AC = 1;	//airconditions
	public static final int TYPE_FR = 2;	//fridge
	public static final int TYPE_WS = 3;	//washing machine
	public static final int TYPE_OTHER = 4;	//other
	private String devId;
	private String devNickName;
	private String devBarCode;
	private int type;
	private int storageCount;//冰箱参数,设备参数 2/3厢
	private String domain;
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public DevInfo(){ }
	
	public DevInfo(String devId, String devNickName, String devBarCode, int type, int storageCount, String domain) {
		this.devId = devId;
		this.devNickName = devNickName;
		this.devBarCode = devBarCode;
		this.type = type;
		this.storageCount = storageCount;
		this.domain = domain;
	}

	public void setDevId(String id) {
		this.devId = id;
	}
	
	public String getDevId() {
		return devId;
	}
	
	public void setNickName(String name) {
		this.devNickName = name;
	}
	
	public String getNickName() {
		return devNickName;
	}	
	
	public void setBarCode(String barcode) {
		this.devBarCode = barcode;
	}
	
	public String getBarCode() {
		return devBarCode;
	}

	public int getStorageCount() {
		return storageCount;
	}

	public void setStorageCount(int storageCount) {
		this.storageCount = storageCount;
	}
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	/*public String generateJsonStr(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("devId", devId);
		jsonObject.put("devNickName", devNickName);
		jsonObject.put("devBarCode", devBarCode);
		jsonObject.put("type", type);
		jsonObject.put("storageCount", storageCount);
		jsonObject.put("domain", domain);
		return jsonObject.toString() ;
	}*/
}
