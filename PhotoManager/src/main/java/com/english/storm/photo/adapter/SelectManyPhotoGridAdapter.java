package com.english.storm.photo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.english.storm.common.util.ScreenUtils;
import com.english.storm.glide.GlideUtils;
import com.english.storm.photo.R;
import com.english.storm.photo.entity.SelectImage;
import com.english.storm.photo.listener.SelectPhotoClickListener;

import java.io.File;
import java.util.ArrayList;


public class SelectManyPhotoGridAdapter extends BaseAdapter {

    private ArrayList<SelectImage> imageList;
    private ArrayList<String> selectList;
    private ArrayList<Integer> selectPositionList;

    private Context context;
    private Button ok_bt;

    public SelectManyPhotoGridAdapter(Context context, Button ok_bt) {
        this.context = context;
        this.ok_bt = ok_bt;
    }

    public void setImageList(ArrayList<SelectImage> imageList, ArrayList<String> selectList, ArrayList<Integer> selectPositionList) {
        this.imageList = imageList;
        if (selectList != null && selectPositionList != null) {
            this.selectList = selectList;
            this.selectPositionList = selectPositionList;
        } else {
            this.selectList = new ArrayList<>();
            this.selectPositionList = new ArrayList<>();
        }
    }

    public ArrayList<SelectImage> getImageList() {
        return imageList;
    }

    public ArrayList<String> getSelectList() {
        return selectList;
    }

    public ArrayList<Integer> getSelectPositionList() {
        return selectPositionList;
    }

    @Override
    public int getCount() {
        if (imageList == null) {
            return 0;
        }
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_photo, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);

            RelativeLayout.LayoutParams itemParams = (RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
            itemParams.height = (ScreenUtils.getScreenWidth(context) - ScreenUtils.dip2px(context, 8)) / 3;//
            holder.imageView.setLayoutParams(itemParams);
            holder.listener = new SelectPhotoClickListener(context, ok_bt, this);
            holder.checkbox.setVisibility(View.VISIBLE);
            holder.checkbox.setOnClickListener(holder.listener);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.listener.setPosition(position);
        holder.checkbox.setChecked(imageList.get(position).isChecked());
        int num = imageList.get(position).getNum();
        if (num > 0) {
            holder.checkbox.setText(num + "");
        } else {
            holder.checkbox.setText("");
        }

        File file = new File(imageList.get(position).getPath());
        GlideUtils.loadAsBitmap(context, file, holder.imageView, 0, 0);

        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        CheckBox checkbox;
        SelectPhotoClickListener listener;
    }


}