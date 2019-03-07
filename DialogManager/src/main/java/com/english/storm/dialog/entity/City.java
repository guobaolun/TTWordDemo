package com.english.storm.dialog.entity;

public class City {

	private String cityName;
	private String cityCode;
	private String provinceCode;

	public City(String cityName, String cityCode, String provinceCode) {
		super();
		this.cityName = cityName;
		this.cityCode = cityCode;
		this.provinceCode = provinceCode;
	}


	public String getCityName() {
		return cityName;
	}


	public String getCityCode() {
		return cityCode;
	}


	public String getProvinceCode() {
		return provinceCode;
	}

}
