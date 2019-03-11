package com.storm.ttword.dialog;


import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.english.storm.widget.wheelview.OnWheelChangedListener;
import com.english.storm.widget.wheelview.WheelView;
import com.english.storm.widget.wheelview.adapters.ArrayWheelAdapter;
import com.storm.common.dialog.BottomSlideDialog;
import com.storm.ttword.R;
import com.storm.ttword.dialog.dao.AddressDao;
import com.storm.ttword.dialog.entity.AreaData;
import com.storm.ttword.dialog.entity.City;
import com.storm.ttword.dialog.entity.CityData;
import com.storm.ttword.dialog.entity.Province;
import com.storm.ttword.dialog.entity.ProvinceData;

public class SelectLocationDialogManager implements View.OnClickListener {

    private Activity activity;
    private OnSelectedListener listener;
    private AddressDao mAddressDao;
    private AreaData mAreaData;


    private ProvinceData mProvinceData;
    private WheelView mProvinceWheel;
    private WheelView mCityWheelView;
    private WheelView mAreaWheelView;
    private CityData mCityData = null;
    private BottomSlideDialog mBottomSlideDialog;
    private TextView cancel_tv;
    private TextView ok_tv;


    public SelectLocationDialogManager(Activity activity, OnSelectedListener listener) {
        this.activity = activity;
        this.listener = listener;
        mAddressDao = new AddressDao(activity);
    }


    public void showDialog(String province, String city, String area) {

        mProvinceData = mAddressDao.queryProvince();

        View view = View.inflate(activity, R.layout.dialog_select_location, null);
        cancel_tv = (TextView) view.findViewById(R.id.cancel_tv);
        cancel_tv.setOnClickListener(this);
        ok_tv = (TextView) view.findViewById(R.id.ok_tv);
        ok_tv.setOnClickListener(this);

        MyOnWheelChangedListener listener = new MyOnWheelChangedListener();

        mProvinceWheel = view.findViewById(R.id.province_wheel);
        mProvinceWheel.setVisibleItems(7);   // 设置可见条目数量
        mProvinceWheel.addChangingListener(listener);

        mCityWheelView = view.findViewById(R.id.city_wheel);
        mCityWheelView.setVisibleItems(7);
        mCityWheelView.addChangingListener(listener);

        mAreaWheelView = view.findViewById(R.id.area_wheel);
        mAreaWheelView.setVisibleItems(7);
        mAreaWheelView.addChangingListener(listener);

        mProvinceWheel.setViewAdapter(new ArrayWheelAdapter<>(activity, mProvinceData.getProvinceArr()));


        int position = 0;
        if (!TextUtils.isEmpty(city)) {
            for (int i = 0; i < mProvinceData.getProvinceArr().length; i++) {
                if (mProvinceData.getProvinceArr()[i].equals(province)) {
                    position = i;
                }
            }
        }

        mProvinceWheel.setCurrentItem(position);

        updateCitys(city, area);

        mBottomSlideDialog = new BottomSlideDialog(activity, R.style.ActionSheetDialogStyle);
        mBottomSlideDialog.setContentView(view);
        mBottomSlideDialog.show();

    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCitys(String city, String area) {
        int provinceCurrent = mProvinceWheel.getCurrentItem();

        Province province = mProvinceData.getProvinceList().get(provinceCurrent);

        mCityData = mAddressDao.queryCity(province.getProvinceCode());
        mCityWheelView.setViewAdapter(new ArrayWheelAdapter<>(activity, mCityData.getCityArr()));


        int position = 0;
        if (!TextUtils.isEmpty(city)) {

            for (int i = 0; i < mCityData.getCityArr().length; i++) {
                if (mCityData.getCityArr()[i].equals(city)) {
                    position = i;
                }
            }
        }


        mCityWheelView.setCurrentItem(position);


        updateAreas(area);
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas(String area) {
        int cityCurrent = mCityWheelView.getCurrentItem();

        City city = mCityData.getCityList().get(cityCurrent);

        mAreaData = mAddressDao.queryArea(city.getCityCode());

        mAreaWheelView.setViewAdapter(new ArrayWheelAdapter<>(activity, mAreaData.getAreaArr()));

        int position = 0;
        if (!TextUtils.isEmpty(area)) {
            for (int i = 0; i < mAreaData.getAreaArr().length; i++) {
                if (mAreaData.getAreaArr()[i].equals(area)) {
                    position = i;
                }
            }
        }

        mAreaWheelView.setCurrentItem(position);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == cancel_tv.getId()) {
            mBottomSlideDialog.dismiss();
        } else if (v.getId() == ok_tv.getId()) {
            if (mProvinceWheel.isScrollingPerformed() || mCityWheelView.isScrollingPerformed() || mAreaWheelView.isScrollingPerformed()) {
                return;
            }

            int provinceCurrent = mProvinceWheel.getCurrentItem();
            int cityCurrent = mCityWheelView.getCurrentItem();
            int districtCurrent = mAreaWheelView.getCurrentItem();

            String provinceStr = mProvinceData.getProvinceList().get(provinceCurrent).getProvinceName();

            String cityStr = "";
            if (mCityData.getCityList().size() > 0) {
                cityStr = mCityData.getCityList().get(cityCurrent).getCityName();
            }

            String areaStr = "";
            if (mAreaData.getAreaList().size() > 0) {
                areaStr = mAreaData.getAreaList().get(districtCurrent).getAreaName();
            }
            listener.onSelected(provinceStr, cityStr, areaStr);

            mBottomSlideDialog.dismiss();
        }

    }


    private class MyOnWheelChangedListener implements OnWheelChangedListener {

        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (wheel == mProvinceWheel) {
                updateCitys("", "");
            } else if (wheel == mCityWheelView) {
                updateAreas("");
            }
        }
    }


    public interface OnSelectedListener {
        void onSelected(String province, String city, String area);
    }
}
