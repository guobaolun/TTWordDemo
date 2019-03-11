package com.storm.ttword.dialog.entity;

import java.util.ArrayList;


public class CityData {
    private ArrayList<City> cityList;
    private String[] cityArr;


    public ArrayList<City> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<City> cityList) {
        this.cityList = cityList;
    }

    public String[] getCityArr() {
        return cityArr;
    }

    public void setCityArr(String[] cityArr) {
        this.cityArr = cityArr;
    }
}
