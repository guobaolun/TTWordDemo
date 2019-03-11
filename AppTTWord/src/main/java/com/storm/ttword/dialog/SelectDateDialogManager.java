package com.storm.ttword.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.english.storm.widget.wheelview.OnWheelChangedListener;
import com.english.storm.widget.wheelview.WheelView;
import com.english.storm.widget.wheelview.adapters.ArrayWheelAdapter;
import com.english.storm.widget.wheelview.adapters.ListWheelAdapter;
import com.storm.common.dialog.BottomSlideDialog;
import com.storm.ttword.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SelectDateDialogManager implements View.OnClickListener {

    /**
     * 年
     */
    private List<String> yearList = new ArrayList<>();
    /**
     * 月
     */
    private String[] mothArr = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
    /**
     * 日
     */
    private List<String> dayList;

    private String selectedYear;// 每次更新时存储到这个里面
    private String selectedMonth;
    private String selectedDay;

    private String currentYear;// 这个只记录第一次获取的时间
    private String currentMonth;
    private String currentDay;

    private Activity activity;
    private OnDateSelectedListener listener;

    private BottomSlideDialog mBottomSlideDialog;
    private WheelView mDayWheelView;
    private WheelView mYearWheel;
    private WheelView mMonthWheelView;


    Calendar calendar = Calendar.getInstance();
    private TextView cancel_tv;
    private TextView ok_tv;

    public SelectDateDialogManager(Activity activity, OnDateSelectedListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public void showDialog(long time) {

        if (time != 0) {
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
            SimpleDateFormat monthFormat = new SimpleDateFormat("M", Locale.getDefault());
            SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());
            currentYear = yearFormat.format(time);// 获取当前年
            currentMonth = monthFormat.format(time);// 获取当前月
            currentDay = dayFormat.format(time);// 获取当前日期
        } else {

            currentYear = calendar.get(Calendar.YEAR) + "";// 获取当前年
            currentMonth = calendar.get(Calendar.MONTH) + 1 + "";// 获取当前月
            currentDay = calendar.get(Calendar.DAY_OF_MONTH) + "";// 获取当前日期
        }


        View view = View.inflate(activity, R.layout.dialog_select_date, null);
        cancel_tv = (TextView) view.findViewById(R.id.cancel_tv);
        cancel_tv.setOnClickListener(this);
        ok_tv = (TextView) view.findViewById(R.id.ok_tv);
        ok_tv.setOnClickListener(this);

        MyOnWheelChangedListener listener = new MyOnWheelChangedListener();

        mYearWheel = (WheelView) view.findViewById(R.id.year_wheel);
        mYearWheel.setCyclic(true);// 设置循环
        mYearWheel.addChangingListener(listener);
        mYearWheel.setVisibleItems(7);   // 设置可见条目数量

        mMonthWheelView = (WheelView) view.findViewById(R.id.month_wheel);
        mMonthWheelView.setCyclic(true);
        mMonthWheelView.addChangingListener(listener);
        mMonthWheelView.setVisibleItems(7);

        mDayWheelView = (WheelView) view.findViewById(R.id.day_wheel);
        mDayWheelView.setCyclic(true);
        mDayWheelView.addChangingListener(listener);
        mDayWheelView.setVisibleItems(7);

        initYear();
        upSelectedYear();
        upSelectedMonth();
        initDate();


        selectedYear = currentYear + "年";// 获取当前年
        selectedMonth = currentMonth + "月";// 获取当前月
        selectedDay = currentDay + "日";// 获取当前日期

        mBottomSlideDialog = new BottomSlideDialog(activity, R.style.ActionSheetDialogStyle);
        mBottomSlideDialog.setContentView(view);
        mBottomSlideDialog.show();

    }


    @Override
    public void onClick(View v) {

        if (v.getId() == ok_tv.getId()){
            listener.onSelected(selectedYear, selectedMonth, selectedDay);
            mBottomSlideDialog.dismiss();
        }else if(v.getId() == cancel_tv.getId()){
            mBottomSlideDialog.dismiss();
        }
    }

    /**
     * 将当前的时间添加进时间选择框
     */
    private void initDate() {
        int yearPosition = 0;
        int monthPosition = 0;
        int dayPosition = 0;
        for (int i = 0; i < yearList.size(); i++) {
            if (yearList.get(i).equals(currentYear + "年")) {
                yearPosition = i;
            }
        }
        for (int i = 0; i < mothArr.length; i++) {
            if (mothArr[i].equals(currentMonth + "月")) {
                monthPosition = i;
            }
        }
        for (int i = 0; i < dayList.size(); i++) {
            if (dayList.get(i).equals(currentDay + "日")) {
                dayPosition = i;
            }
        }
        mYearWheel.setCurrentItem(yearPosition);
        mMonthWheelView.setCurrentItem(monthPosition);
        mDayWheelView.setCurrentItem(dayPosition);
    }


    /**
     * 获取上下两百年的年份信息
     */
    private void initYear() {

        int startYear = calendar.get(Calendar.YEAR) - 100;
        for (int i = 0; i < 200; i++){
            yearList.add((startYear + i) + "年");
        }
    }

    /**
     * 根据当前的年月来获取日的信息
     *
     * @param year  年
     * @param month 月
     */
    private void getday(int year, int month) {
        dayList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.DATE, -1);
        int maxdate = calendar.get(Calendar.DATE);
        for (int i = 1; i <= maxdate + 1; i++) {
            dayList.add(i + "日");
        }
        mDayWheelView.setViewAdapter(new ListWheelAdapter<>(activity, dayList));
        upSelectedDay();
    }


    private class MyOnWheelChangedListener implements OnWheelChangedListener {

        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (wheel == mYearWheel) {// 滑动年滚轮的事件
                upSelectedYear();
            } else if (wheel == mMonthWheelView) {// 滑动月滚轮的事件
                upSelectedMonth();
            } else if (wheel == mDayWheelView) {// 滑动日滚轮的事件
                upSelectedDay();
            }
        }
    }


    private void upSelectedYear() {// 将年份的数据添加进滚轮中
        mYearWheel.setViewAdapter(new ListWheelAdapter<>(activity, yearList));
        try {
            int currentPosition = mYearWheel.getCurrentItem();
            // 将当前的年赋值给全局
            selectedYear = yearList.get(currentPosition);
            // 因为在第一次进入的时候是还没有月份的数据的，所以需要排除掉
            if (selectedMonth != null) {
                getday(Integer.parseInt(selectedYear.replace("年", "")), Integer.parseInt(selectedMonth.replace("月", "")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void upSelectedMonth() {
        mMonthWheelView.setViewAdapter(new ArrayWheelAdapter<>(activity, mothArr));
        try {
            int currentPosition = mMonthWheelView.getCurrentItem();
            // 将当前的月赋值给全局
            selectedMonth = mothArr[currentPosition];
            getday(Integer.parseInt(selectedYear.replace("年", "")), Integer.parseInt(selectedMonth.replace("月", "")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void upSelectedDay() {
        try {
            int currentPosition = mDayWheelView.getCurrentItem();
            selectedDay = dayList.get(currentPosition);
        } catch (Exception e) {
            mDayWheelView.setCurrentItem(dayList.size() - 1);
            selectedDay = dayList.get(dayList.size() - 1);
        }
    }


    public interface OnDateSelectedListener {
        void onSelected(String year, String month, String day);
    }
}
