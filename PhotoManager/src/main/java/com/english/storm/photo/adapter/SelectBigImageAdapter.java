package com.english.storm.photo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.english.storm.common.util.ScreenUtils;
import com.english.storm.glide.GlideUtils;
import com.english.storm.photo.R;
import com.english.storm.photo.entity.SelectImage;

import java.util.ArrayList;

public class SelectBigImageAdapter extends PagerAdapter {
    private Context context;

    private ArrayList<SelectImage> imageList;
    private ArrayList<String> selectList;

    public SelectBigImageAdapter(Context context) {
        this.context = context;
    }


    public void setImageList(ArrayList<SelectImage> imageList, ArrayList<String> selectList) {
        this.imageList = imageList;
        if (selectList != null) {
            this.selectList = selectList;

        } else {
            this.selectList = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        if (imageList == null) {
            return 0;
        }
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.item_big_image, null);
        ImageView mImageView = (ImageView) view.findViewById(R.id.imageview);


        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
        lp.height = ScreenUtils.getScreenHeight(context);
        lp.width = ScreenUtils.getScreenWidth(context);
        mImageView.setLayoutParams(lp);


        GlideUtils.loadAsBitmap(context, imageList.get(position % imageList.size()).getPath(), mImageView, 0, 0);
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}