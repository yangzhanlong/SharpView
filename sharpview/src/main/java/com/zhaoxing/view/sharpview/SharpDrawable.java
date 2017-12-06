package com.zhaoxing.view.sharpview;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

class SharpDrawable extends Drawable {

    void setBgColor(int bgColor) {
        mBgColor = bgColor;
    }

    void setCornerRadius(float cornerRadius) {
        mCornerRadius = cornerRadius;
    }

    void setArrowDirection(SharpView.ArrowDirection arrowDirection) {
        mArrowDirection = arrowDirection;
    }

    void setRelativePosition(float relativePosition) {
        mRelativePosition = Math.min(Math.max(relativePosition,0),1);
    }

    void setSharpSize(float sharpSize) {
        mSharpSize = sharpSize;
    }

    private float mSharpSize;

    private int mBgColor;

    private float mCornerRadius;

    private SharpView.ArrowDirection mArrowDirection;

    /**
     * from 0 to 1
     */
    private float mRelativePosition;

    private Paint mPaint;

    private RectF mRect;

    private Path mPath;

    private PointF[] mPointFs;

    SharpDrawable() {
        mPaint = new Paint();
        mRect = new RectF();
        mPointFs = new PointF[3];
        mPath = new Path();
        mPointFs[0] = new PointF();
        mPointFs[1] = new PointF();
        mPointFs[2] = new PointF();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        mPaint.setColor(mBgColor);
        Rect bounds = getBounds();
        int left = bounds.left;
        int top = bounds.top;
        int right = bounds.right;
        int bottom = bounds.bottom;
        float length ;
        switch (mArrowDirection){
            case LEFT:
                left += mSharpSize;
                length = Math.max(mRelativePosition * bounds.height() + bounds.top, mSharpSize + mCornerRadius);
                length = Math.min(length,bounds.height() - mSharpSize - mCornerRadius);
                mPointFs[0].set(bounds.left,length + bounds.top);
                mPointFs[1].set(mPointFs[0].x + mSharpSize,mPointFs[0].y - mSharpSize);
                mPointFs[2].set(mPointFs[0].x + mSharpSize,mPointFs[0].y + mSharpSize);
                break;
            case TOP:
                top += mSharpSize;
                length = Math.max(mRelativePosition * bounds.width() + bounds.top, mSharpSize + mCornerRadius);
                length = Math.min(length,bounds.width() - mSharpSize - mCornerRadius);
                mPointFs[0].set(bounds.left + length,bounds.top);
                mPointFs[1].set(mPointFs[0].x - mSharpSize, mPointFs[0].y + mSharpSize);
                mPointFs[2].set(mPointFs[0].x + mSharpSize, mPointFs[0].y + mSharpSize);
                break;
            case RIGHT:
                right -= mSharpSize;
                length = Math.max(mRelativePosition * bounds.height() + bounds.top, mSharpSize + mCornerRadius);
                length = Math.min(length,bounds.height() - mSharpSize - mCornerRadius);
                mPointFs[0].set(bounds.right,length + bounds.top);
                mPointFs[1].set(mPointFs[0].x - mSharpSize,mPointFs[0].y - mSharpSize);
                mPointFs[2].set(mPointFs[0].x - mSharpSize,mPointFs[0].y + mSharpSize);
                break;
            case BOTTOM:
                bottom -= mSharpSize;
                length = Math.max(mRelativePosition * bounds.width() + bounds.top, mSharpSize + mCornerRadius);
                length = Math.min(length,bounds.width() - mSharpSize - mCornerRadius);
                mPointFs[0].set(bounds.left + length,bounds.bottom);
                mPointFs[1].set(mPointFs[0].x - mSharpSize, mPointFs[0].y - mSharpSize);
                mPointFs[2].set(mPointFs[0].x + mSharpSize, mPointFs[0].y - mSharpSize);
                break;
        }
        mRect.set(left,top,right,bottom);

        mPath.reset();
        mPath.moveTo(mPointFs[0].x, mPointFs[0].y);
        mPath.lineTo(mPointFs[1].x, mPointFs[1].y);
        mPath.lineTo(mPointFs[2].x, mPointFs[2].y);
        mPath.close();
        mPaint.setAntiAlias(true);
        canvas.drawRoundRect(mRect,mCornerRadius,mCornerRadius,mPaint);
        canvas.drawPath(mPath,mPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}