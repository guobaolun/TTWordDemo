package com.storm.common.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.storm.common.activity.BaseActivity;


/**
 * @author guobaolun
 * @since 2018/3/9
 */

public interface BaseView {
    Context getContext();
    void showToast(String msg);

    void finish();
    void startActivity(Intent intent);
    void startActivityForResult(Intent intent, int requestCode);
    void finishActivity(Class<?> cls);
    void setResult(int resultCode);

    BaseActivity getBaseActivity();



}
