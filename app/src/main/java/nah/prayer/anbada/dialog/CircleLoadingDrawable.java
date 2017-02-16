package nah.prayer.anbada.dialog;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

public class CircleLoadingDrawable extends Drawable {

    private static final int ANGLE_360 = 360;
    private static final int START_ANGLE = -15;
    private static final float MAX_SWIPE_ANGLE = 0.95F * ANGLE_360;

    private static final long ANIMATION_DURATION = 1000;
    private static final float DEFAULT_SIZE = 56.0f;
    private static final float DEFAULT_CENTER_RADIUS = 12.5f;
    private static final float DEFAULT_STROKE_WIDTH = 2.5f;
    private static final float COLOR_START_DELAY_OFFSET = 0.8f;
    private static final float END_TRIM_DURATION_OFFSET = 1.0f;
    private static final float START_TRIM_DURATION_OFFSET = 0.5f;

    private float mWidth;
    private float mHeight;
    private float mStrokeWidth;
    private float mCenterRadius;
    private float mGroupRotation;
    private float mStrokeInset;
    private float mStartAngle;
    private float mSweepAngle;

    private long mDuration;


    private Paint mPaint;
    private int mCurrentColor;
    private ValueAnimator mAnimator;
    private Context mContext;

    public CircleLoadingDrawable(Context context) {
        setUpPaint();
        setDefaultParams(context);
        setUpAnimators();
        start();
    }

    @Override
    public void draw(Canvas canvas) {
        int saveCount = canvas.save();

        canvas.rotate(mGroupRotation, getBounds().exactCenterX(), getBounds().exactCenterY());

        RectF arcBounds = new RectF();
        //arcBounds.Set(10,10,Bounds.Width()-10,Bounds.Height()-10);
        arcBounds.set(getBounds());
        //arcBounds.Inset(500, 500);

        mPaint.setColor(mCurrentColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStrokeWidth(mStrokeWidth);
        //canvas.DrawArc(arcBounds, 0, 360, false, mPaint);
        canvas.drawArc(arcBounds, mStartAngle, mSweepAngle, false, mPaint);

        arcBounds.set(15f, 15f, getBounds().width() - 15f, getBounds().height() - 15f);
        mPaint.setColor(mCurrentColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawArc(arcBounds, 0, 360, true, mPaint);

        canvas.restoreToCount(saveCount);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public void start() {
        resetOriginals();
        mAnimator.setDuration(mDuration);
        mAnimator.start();
    }

    public void stop() {
        mAnimator.cancel();
    }

    private void resetOriginals() {
        mStartAngle = 315;
        mSweepAngle = 315;
    }

    private void setDefaultParams(Context context) {
        mCurrentColor = Color.WHITE;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float screenDensity = metrics.density;

        mWidth = DEFAULT_SIZE * screenDensity;
        mHeight = DEFAULT_SIZE * screenDensity;
        mStrokeWidth = DEFAULT_STROKE_WIDTH * screenDensity;
        mCenterRadius = DEFAULT_CENTER_RADIUS * screenDensity;

        mDuration = ANIMATION_DURATION;

        mStrokeWidth=10*screenDensity;
    }

    private void setUpAnimators() {
        mAnimator = ValueAnimator.ofFloat(0.0f, 2.0f);
        mAnimator.setRepeatCount(-1);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();

                if (progress <= 1f) {
                    //mSweepAngle = ( 0 - MAX_SWIPE_ANGLE * progress);
                    //mStartAngle = 315 - MAX_SWIPE_ANGLE * progress/10;
                    mStartAngle = 315 - MAX_SWIPE_ANGLE * progress / 10;
                    mSweepAngle = 360 - MAX_SWIPE_ANGLE * progress;
                }
                if (progress > 1 && progress <= 2) {
                    //mStartAngle = 315 - MAX_SWIPE_ANGLE * 0.1f - MAX_SWIPE_ANGLE * (progress - 1)- MAX_SWIPE_ANGLE * (progress - 1) / 10;
                    //mSweepAngle = -MAX_SWIPE_ANGLE + MAX_SWIPE_ANGLE * (progress - 1)  ;
                    mStartAngle = 315 - MAX_SWIPE_ANGLE / 10 - MAX_SWIPE_ANGLE * (progress - 1) / 10;
                    mSweepAngle = 0 - MAX_SWIPE_ANGLE * (progress - 1);
                }

                invalidateSelf();
            }
        });
    }

    private void setUpPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
}
