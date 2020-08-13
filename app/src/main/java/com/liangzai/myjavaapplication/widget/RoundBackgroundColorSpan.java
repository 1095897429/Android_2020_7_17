package com.liangzai.myjavaapplication.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

import com.blankj.utilcode.util.SizeUtils;
import com.socks.library.KLog;

/**
 * @author zhouliang
 * 版本 1.0
 * 创建时间 2020/7/23
 * 描述: 自定义富文本(给文本设置边框)
 */
public class RoundBackgroundColorSpan extends ReplacementSpan {
    private int bgColor;
    private int textColor;
    private int radius;
    private int mSize;

    public RoundBackgroundColorSpan(int bgColor, int textColor) {
        super();
        this.bgColor = bgColor;
        this.textColor = textColor;
        radius = SizeUtils.dp2px(3f);
    }

    //文字宽度
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        paint.setTextSize(SizeUtils.sp2px(11));
        mSize = ((int) paint.measureText(text, start, end) );
        KLog.d("tag","mSize宽度1是 " + mSize);
        //返回的是整体文字的宽度
        return mSize + SizeUtils.dp2px(6 * 2);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        //mSize就是span的宽度，span有多宽，开发者可以在这里随便定义规则
        paint.setColor(this.bgColor);
        paint.setAntiAlias(true);
        RectF oval = new RectF(x, y + paint.ascent() - SizeUtils.dp2px(3),
                x +  (mSize + SizeUtils.dp2px(6 * 2))  , y + paint.descent() -  SizeUtils.dp2px(0));
        canvas.drawRoundRect(oval, radius, radius, paint);

        paint.setColor(this.textColor);
        paint.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
        //1 计算baseline 位置
        //2 也可以用center + distance 计算出baseline位置
        float baseline = oval.centerY()+distance;
        //以中心绘制文字
        canvas.drawText(text, start, end, x + (mSize + SizeUtils.dp2px(6 * 2)) / 2 , baseline, paint);
    }
}
