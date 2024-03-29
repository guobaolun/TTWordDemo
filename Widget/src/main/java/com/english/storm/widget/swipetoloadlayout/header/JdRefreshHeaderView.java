package com.english.storm.widget.swipetoloadlayout.header;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.english.storm.widget.R;

/**
 * Created by Aspsine on 2015/11/5.
 */
public class JdRefreshHeaderView extends RelativeLayout implements SwipeTrigger, SwipeRefreshTrigger {

    private ImageView ivSpeed;

    private AnimationDrawable mAnimDrawable;

    private Animation mTwinkleAnim;


    public JdRefreshHeaderView(Context context) {
        super(context);
    }

    public JdRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JdRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ImageView ivRefresh = findViewById(R.id.ivRefresh);
        ivSpeed = findViewById(R.id.ivSpeed);
        mAnimDrawable = (AnimationDrawable) ivRefresh.getBackground();
        mTwinkleAnim = AnimationUtils.loadAnimation(getContext(), R.anim.twinkle);
    }

    @Override
    public void onRefresh() {
        ivSpeed.setVisibility(VISIBLE);
        ivSpeed.startAnimation(mTwinkleAnim);
        if (!mAnimDrawable.isRunning()) {
            mAnimDrawable.start();
        }
    }

    @Override
    public void onPrepare() {
        ivSpeed.clearAnimation();
        ivSpeed.setVisibility(GONE);
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
    }

    @Override
    public void onRelease() {
        if (!mAnimDrawable.isRunning()) {
            mAnimDrawable.start();
        }
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onReset() {
        mAnimDrawable.stop();
        ivSpeed.clearAnimation();
        ivSpeed.setVisibility(GONE);
    }
}
