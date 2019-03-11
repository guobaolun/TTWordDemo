package com.storm.ttword.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.english.storm.widget.wheelview.WheelView;
import com.english.storm.widget.wheelview.adapters.ArrayWheelAdapter;
import com.storm.common.dialog.BottomSlideDialog;
import com.storm.ttword.R;

/**
 * @author guobaolun
 */
public class SelectDialogManager {

    private BottomSlideDialog mSelectDialog;
    private int pisiton = 0;

    public void showDialog(final Activity activity, final String[] textArr, String defText, final OnSelectedListener listener) {

        for (int i = 0; i < textArr.length; i++) {

            if (textArr[i].equals(defText)) {
                pisiton = i;
            }
        }

        View view = View.inflate(activity, R.layout.dialog_select, null);
        TextView cancelTv = view.findViewById(R.id.cancel_tv);

        final WheelView wheel = view.findViewById(R.id.wheel);
        // 设置可见条目数量
        wheel.setVisibleItems(7);
        wheel.setViewAdapter(new ArrayWheelAdapter<>(activity, textArr));
        wheel.setCurrentItem(pisiton);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectDialog.dismiss();
            }
        });
        TextView okTv = view.findViewById(R.id.ok_tv);
        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textArr[wheel.getCurrentItem()];
                listener.onSelected(text);
                mSelectDialog.dismiss();
            }
        });

        mSelectDialog = new BottomSlideDialog(activity, R.style.ActionSheetDialogStyle);
        mSelectDialog.setContentView(view);
        mSelectDialog.show();
    }

    public interface OnSelectedListener {
        void onSelected(String text);
    }

}
