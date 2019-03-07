package com.english.storm.dialog.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.english.storm.dialog.entity.Area;
import com.english.storm.dialog.entity.AreaData;
import com.english.storm.dialog.entity.City;
import com.english.storm.dialog.entity.CityData;
import com.english.storm.dialog.entity.Province;
import com.english.storm.dialog.entity.ProvinceData;

import java.util.ArrayList;


public class AddressDao {

    private String path ;


    public AddressDao(Context context) {
        AddressHelper dbManager = new AddressHelper(context);
        path = AddressHelper.DB_PATH + "/" + AddressHelper.DB_NAME;
        dbManager.openDateBase();
        dbManager.closeDatabase();
    }

    /**
     * 查询省数据
     */
    public ProvinceData queryProvince() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path, null);

        Cursor c = db.query(
                "province",
                new String[]{"provinceName", "provinceCode", "flag"},
                null,
                null,
                null,
                null,
                null);
        ArrayList<Province> provinceList = new ArrayList<>();
        String[] provinceArr = new String[c.getCount()];
        int i = 0;
        while (c.moveToNext()) {
            String provinceName = c.getString(0);
            String provinceCode = c.getString(1);
            String flag = c.getString(2);
            Province searchRecord = new Province(provinceName, provinceCode, flag);
            provinceList.add(searchRecord);
            provinceArr[i++] = provinceName;
        }
        c.close();
        db.close(); // 关闭数据库

        ProvinceData provinceData = new ProvinceData();
        provinceData.setProvinceList(provinceList);
        provinceData.setProvinceArr(provinceArr);
        return provinceData;
    }

    /**
     * 查询市数据
     *
     * @param provinceCode provinceCode
     */
    public CityData queryCity(String provinceCode) {

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path, null);
        Cursor c = db.query(
                "city",
                new String[]{"cityName", "cityCode"},
                "provinceCode = ?",
                new String[]{provinceCode},
                null,
                null,
                null);
        ArrayList<City> list = new ArrayList<>();
        String[] cityArr = new String[c.getCount()];
        int i = 0;
        while (c.moveToNext()) {
            String cityName = c.getString(0);
            String cityCode = c.getString(1);

            City city = new City(cityName, cityCode, provinceCode);
            list.add(city);
            cityArr[i++] = cityName;
        }

        c.close();
        db.close(); // 关闭数据库

        CityData cityData = new CityData();
        cityData.setCityList(list);
        cityData.setCityArr(cityArr);
        return cityData;
    }


    /**
     * 查询区数据
     *
     */

    /**
     * 查询区数据
     *
     * @param cityCode cityCode
     */
    public AreaData queryArea(String cityCode) {
        /**
         * Cursor CrashHandler = db.rawQuery("SELECT name, balance FROM account WHERE _id=?",new String[]{ id + "" });// 执行查询操作, 得到Cursor对象
         *
         * Cursor CrashHandler = db.query( "表名" , 要查的列名 , 条件 , 条件问号参数 , 分组 , 分组条件 , 排序 );
         */
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path, null);

        Cursor c = db.query(
                "area",
                new String[]{"areaName", "areaCode"},
                "cityCode = ?",
                new String[]{cityCode},
                null,
                null,
                null);
        ArrayList<Area> list = new ArrayList<>();
        String[] areaArr = new String[c.getCount()];
        int i = 0;
        while (c.moveToNext()) {
            String areaName = c.getString(0);
            String areaCode = c.getString(1);
            Area area = new Area(areaName, areaCode, cityCode);
            list.add(area);
            areaArr[i++] = areaName;
        }
        c.close();
        db.close(); // 关闭数据库

        AreaData areaData = new AreaData();
        areaData.setAreaList(list);
        areaData.setAreaArr(areaArr);
        return areaData;
    }

}
