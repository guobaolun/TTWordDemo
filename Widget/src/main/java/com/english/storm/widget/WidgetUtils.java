package com.english.storm.widget;

import android.content.Context;

import java.lang.reflect.Field;

class WidgetUtils {

	/**
	 * 获取状态栏高度
     */
	public static int getStatusBarHeight(Context context) {

		int sbar = 0;

		try {

			Class<?> c = Class.forName("com.android.internal.R$dimen");

			Object obj = c.newInstance();

			Field field = c.getField("status_bar_height");

			int x = Integer.parseInt(field.get(obj).toString());

			sbar = context.getResources().getDimensionPixelSize(x);

		} catch (Exception e1) {

			e1.printStackTrace();

		}

		return sbar;
	}



	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 把密度转换为像素
	 */
	static int dip2px(Context context, float px) {
		final float scale = getScreenDensity(context);
		return (int) (px * scale + 0.5);
	}


	// 将sp值转换为px值
	static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}


	/**
	 * 得到设备屏幕的高度
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 得到设备屏幕的宽度
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 得到设备的密度	
	 */
	private static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}
	


}
