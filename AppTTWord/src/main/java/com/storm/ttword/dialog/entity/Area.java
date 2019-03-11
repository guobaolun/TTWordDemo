package com.storm.ttword.dialog.entity;

public class Area {

	private String areaName;
	private String areaCode;
	private String cityCode;
	
	
	public Area(String areaName, String areaCode, String cityCode) {
		super();
		this.areaName = areaName;
		this.areaCode = areaCode;
		this.cityCode = cityCode;
	}


	public String getAreaName() {
		return areaName;
	}


	public String getAreaCode() {
		return areaCode;
	}


	public String getCityCode() {
		return cityCode;
	}
	

}
