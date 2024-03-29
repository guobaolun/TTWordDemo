package com.english.storm.widget.swipetoloadlayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.animation.Animation;

import com.english.storm.widget.R;
import com.lsjwzh.widget.materialloadingprogressbar.MaterialProgressDrawable;

/**
 * Private class created to work around issues with AnimationListeners being
 * called before the animation is actually complete and support shadows on older
 * platforms.
 */
public class GoogleCircleProgressView extends AppCompatImageView {


    private static final int KEY_SHADOW_COLOR = 0x1E000000;
    private static final int FILL_SHADOW_COLOR = 0x3D000000;
    // PX
    private static final float X_OFFSET = 0f;
    private static final float Y_OFFSET = 1.75f;
    private static final float SHADOW_RADIUS = 3.5f;
    private static final int SHADOW_ELEVATION = 4;


    private static final int DEFAULT_CIRCLE_BG_LIGHT = 0xFFFAFAFA;
    private static final int DEFAULT_CIRCLE_DIAMETER = 56;
    private static final int STROKE_WIDTH_LARGE = 3;
    public static final int DEFAULT_TEXT_SIZE = 9;

    private Animation.AnimationListener mListener;
    private int mShadowRadius;
    private int mBackGroundColor;
    private int mProgressStokeWidth;
    private int mArrowWidth;
    private int mArrowHeight;
    private int mProgress;
    private int mMax;
    private int mInnerRadius;
    private Paint mTextPaint;
    private int mTextSize;
    private boolean mIfDrawText;
    private boolean mShowArrow;
    private MaterialProgressDrawable mProgressDrawable;
    private boolean mCircleBackgroundEnabled;
    private int[] mColors = new int[]{Color.BLACK};

    public GoogleCircleProgressView(Context context) {
        super(context);
        init(context, null, 0);

    }

    public GoogleCircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);

    }

    public GoogleCircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.GoogleCircleProgressView, defStyleAttr, 0);
        final float density = getContext().getResources().getDisplayMetrics().density;

        mBackGroundColor = a.getColor(
                R.styleable.GoogleCircleProgressView_gcp_background_color, DEFAULT_CIRCLE_BG_LIGHT);

        int mProgressColor = a.getColor(
                R.styleable.GoogleCircleProgressView_gcp_progress_color, DEFAULT_CIRCLE_BG_LIGHT);
        mColors = new int[]{mProgressColor};

        mInnerRadius = a.getDimensionPixelOffset(
                R.styleable.GoogleCircleProgressView_gcp_inner_radius, -1);

        mProgressStokeWidth = a.getDimensionPixelOffset(
                R.styleable.GoogleCircleProgressView_gcp_progress_stoke_width, (int) (STROKE_WIDTH_LARGE * density));
        mArrowWidth = a.getDimensionPixelOffset(
                R.styleable.GoogleCircleProgressView_gcp_arrow_width, -1);
        mArrowHeight = a.getDimensionPixelOffset(
                R.styleable.GoogleCircleProgressView_gcp_arrow_height, -1);
        mTextSize = a.getDimensionPixelOffset(
                R.styleable.GoogleCircleProgressView_gcp_progress_text_size, (int) (DEFAULT_TEXT_SIZE * density));
        int mTextColor = a.getColor(
                R.styleable.GoogleCircleProgressView_gcp_progress_text_color, Color.BLACK);

        mShowArrow = a.getBoolean(R.styleable.GoogleCircleProgressView_gcp_show_arrow, true);
        mCircleBackgroundEnabled = a.getBoolean(R.styleable.GoogleCircleProgressView_gcp_enable_circle_background, true);


        mProgress = a.getInt(R.styleable.GoogleCircleProgressView_gcp_progress, 0);
        mMax = a.getInt(R.styleable.GoogleCircleProgressView_gcp_max, 100);
        int textVisible = a.getInt(R.styleable.GoogleCircleProgressView_gcp_progress_text_visibility, 1);
        if (textVisible != 1) {
            mIfDrawText = true;
        }

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        a.recycle();
        mProgressDrawable = new MaterialProgressDrawable(getContext(), this);
        super.setImageDrawable(mProgressDrawable);
    }


    private boolean elevationSupported() {
        return android.os.Build.VERSION.SDK_INT >= 21;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!elevationSupported()) {
            setMeasuredDimension(getMeasuredWidth() + mShadowRadius * 2, getMeasuredHeight()
                    + mShadowRadius * 2);
        }
    }

    private OvalShape ovalShape = new OvalShape();
    private OvalShadow ovalShadow = new OvalShadow();
    private ShapeDrawable mBgCircle = new ShapeDrawable(ovalShape);

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        final float density = getContext().getResources().getDisplayMetrics().density;
        int mDiameter = Math.min(getMeasuredWidth(), getMeasuredHeight());
        if (mDiameter <= 0) {
            mDiameter = (int) density * DEFAULT_CIRCLE_DIAMETER;
        }
        if (getBackground() == null && mCircleBackgroundEnabled) {
            final int shadowYOffset = (int) (density * Y_OFFSET);
            final int shadowXOffset = (int) (density * X_OFFSET);
            mShadowRadius = (int) (density * SHADOW_RADIUS);

            if (elevationSupported()) {
                mBgCircle.setShape(ovalShape);
                ViewCompat.setElevation(this, SHADOW_ELEVATION * density);
            } else {
                ovalShadow.setRadialGradient(mShadowRadius, mDiameter - mShadowRadius * 2);
                mBgCircle.setShape(ovalShadow);
                ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, mBgCircle.getPaint());
                mBgCircle.getPaint().setShadowLayer(mShadowRadius, shadowXOffset, shadowYOffset, KEY_SHADOW_COLOR);
                final int padding = mShadowRadius;
                // set padding so the inner image sits correctly within the shadow.
                setPadding(padding, padding, padding, padding);
            }
            mBgCircle.getPaint().setColor(mBackGroundColor);
            setBackgroundDrawable(mBgCircle);
        }
        mProgressDrawable.setBackgroundColor(mBackGroundColor);
        mProgressDrawable.setColorSchemeColors(mColors);
        mProgressDrawable.setSizeParameters(mDiameter, mDiameter,
                mInnerRadius <= 0 ? (mDiameter - mProgressStokeWidth * 2) >> 2 : mInnerRadius,
                mProgressStokeWidth,
                mArrowWidth < 0 ? mProgressStokeWidth * 4 : mArrowWidth,
                mArrowHeight < 0 ? mProgressStokeWidth * 2 : mArrowHeight);
        if (isShowArrow()) {
            mProgressDrawable.showArrowOnFirstStart(true);
            mProgressDrawable.setArrowScale(1f);
            mProgressDrawable.showArrow(true);
        }
        super.setImageDrawable(null);
        super.setImageDrawable(mProgressDrawable);
        mProgressDrawable.setAlpha(255);
        mProgressDrawable.setStartEndTrim(0f, 0.75f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIfDrawText) {
            String text = String.format("%s%%", mProgress);
            int x = getWidth() / 2 - text.length() * mTextSize / 4;
            int y = getHeight() / 2 + mTextSize / 4;
            canvas.drawText(text, x, y, mTextPaint);
        }
    }

    @Override
    final public void setImageResource(int resId) {

    }


    public boolean isShowArrow() {
        return mShowArrow;
    }

    public void setShowArrow(boolean showArrow) {
        this.mShowArrow = showArrow;
    }


    @Override
    final public void setImageURI(Uri uri) {
        super.setImageURI(uri);
    }

    @Override
    final public void setImageDrawable(Drawable drawable) {
    }

    public void setAnimationListener(Animation.AnimationListener listener) {
        mListener = listener;
    }

    @Override
    public void onAnimationStart() {
        super.onAnimationStart();
        if (mListener != null) {
            mListener.onAnimationStart(getAnimation());
        }
    }

    @Override
    public void onAnimationEnd() {
        super.onAnimationEnd();
        if (mListener != null) {
            mListener.onAnimationEnd(getAnimation());
        }
    }


    /**
     * Set the color resources used in the progress animation from color resources.
     * The first color will also be the color of the bar that grows in response
     * to a user swipe gesture.
     *
     * @param colorResIds colorResIds
     */
    public void setColorSchemeResources(int... colorResIds) {
        final Resources res = getResources();
        int[] colorRes = new int[colorResIds.length];
        for (int i = 0; i < colorResIds.length; i++) {
            colorRes[i] = res.getColor(colorResIds[i]);
        }
        setColorSchemeColors(colorRes);
    }

    /**
     * Set the colors used in the progress animation. The first
     * color will also be the color of the bar that grows in response to a user
     * swipe gesture.
     *
     * @param colors colors
     */
    public void setColorSchemeColors(int... colors) {
        mColors = colors;
        if (mProgressDrawable != null) {
            mProgressDrawable.setColorSchemeColors(colors);
        }
    }

    /**
     * Update the background color of the mBgCircle image view.
     */
    @SuppressLint("ResourceType")
    public void setBackgroundColor(@ColorRes int colorRes) {
        if (getBackground() instanceof ShapeDrawable) {
            final Resources res = getResources();
            ((ShapeDrawable) getBackground()).getPaint().setColor(res.getColor(colorRes));
        }
    }

    public boolean isShowProgressText() {
        return mIfDrawText;
    }

    public void setShowProgressText(boolean mIfDrawText) {
        this.mIfDrawText = mIfDrawText;
    }

    public int getMax() {
        return mMax;
    }

    public void setMax(int max) {
        mMax = max;
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        if (getMax() > progress) {
            mProgress = progress;
        }
    }

    public boolean isRunning() {
        return mProgressDrawable.isRunning();
    }

    public void start() {
        if (!mProgressDrawable.isRunning()) {
            mProgressDrawable.start();
        }
    }

    public void stop() {
        if (mProgressDrawable.isRunning()) {
            mProgressDrawable.stop();
        }
    }

    public void setProgressRotation(float rotation) {
        if (mProgressDrawable.isRunning()) {
            stop();
        }
        mProgressDrawable.showArrow(true);
        mProgressDrawable.showArrowOnFirstStart(true);
        mProgressDrawable.setProgressRotation(rotation);
    }

    public void setStartEndTrim(float start, float end) {
        mProgressDrawable.setStartEndTrim(start, end);
    }

    public boolean circleBackgroundEnabled() {
        return mCircleBackgroundEnabled;
    }

    public void setCircleBackgroundEnabled(boolean enableCircleBackground) {
        this.mCircleBackgroundEnabled = enableCircleBackground;
    }

    @Override
    public int getVisibility() {
        return super.getVisibility();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (mProgressDrawable != null) {
            mProgressDrawable.setVisible(visibility == VISIBLE, false);
            if (visibility != VISIBLE) {
                mProgressDrawable.stop();
            } else {
                if (mProgressDrawable.isRunning()) {
                    mProgressDrawable.stop();
                }
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mProgressDrawable != null) {
            mProgressDrawable.stop();
            mProgressDrawable.setVisible(getVisibility() == VISIBLE, false);

            requestLayout();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mProgressDrawable != null) {
            mProgressDrawable.stop();
            mProgressDrawable.setVisible(false, false);
        }
    }


    private class OvalShadow extends OvalShape {
        private RadialGradient mRadialGradient;
        private int mShadowRadius;
        private Paint mShadowPaint;
        private int mCircleDiameter;



        private OvalShadow() {
            super();
        }

        private void setRadialGradient(int shadowRadius,int circleDiameter) {
            mShadowPaint = new Paint();
            mShadowRadius = shadowRadius;
            mCircleDiameter = circleDiameter;
            mRadialGradient = new RadialGradient(mCircleDiameter >> 1, mCircleDiameter >> 1, mShadowRadius, new int[]{FILL_SHADOW_COLOR, Color.TRANSPARENT}, null, Shader.TileMode.CLAMP);
            mShadowPaint.setShader(mRadialGradient);
        }


        @Override
        public void draw(Canvas canvas, Paint paint) {
            final int viewWidth = GoogleCircleProgressView.this.getWidth();
            final int viewHeight = GoogleCircleProgressView.this.getHeight();
            canvas.drawCircle(viewWidth >> 1, viewHeight >> 1, ((mCircleDiameter >> 1) + mShadowRadius),
                    mShadowPaint);
            canvas.drawCircle(viewWidth >> 1, viewHeight >> 1, (mCircleDiameter >> 1), paint);
        }
    }
}
