package com.english.storm.dialog.entity;

public class Province{

	private String provinceName;
	private String provinceCode;
	private String flag;

	
	
	public Province(String provinceName, String provinceCode, String flag) {
		super();
		this.provinceName = provinceName;
		this.provinceCode = provinceCode;
		this.flag = flag;
	}


	public String getProvinceName() {
		return provinceName;
	}


	public String getProvinceCode() {
		return provinceCode;
	}


	public String getFlag() {
		return flag;
	}



}
