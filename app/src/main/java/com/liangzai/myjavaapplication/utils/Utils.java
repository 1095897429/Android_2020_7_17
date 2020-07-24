package com.liangzai.myjavaapplication.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.liangzai.myjavaapplication.BuildConfig;
import com.liangzai.myjavaapplication.widget.CustomImageSpan;
import com.liangzai.myjavaapplication.widget.RoundBackgroundColorSpan;
import com.socks.library.KLog;

/**
 * @author zhouliang
 * 版本 1.0
 * 创建时间 2020/7/21
 * 描述:
 * 1.倒计时整理
 */
public class Utils {


    public static void initTimeOut(){
        //第一种方式 -- countDownTimer -- 本质是handler
        CountDownTimer timer = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //还剩下多少秒 millisUntilFinished/1000，依次为2、1、0
                KLog.e("tag", "CountDownTimer seconds : " + (millisUntilFinished / 1000 ));
            }

            @Override
            public void onFinish() {
                //结束后的操作
                KLog.e("tag", "CountDownTimer 结束");
            }
        };
        timer.start();

    }




    //设置不同的tag
    public static void setTitleTag(String tag, String title, String id, TextView textView, int bg, int textbg) {
        if(!TextUtils.isEmpty(tag)){
            SpannableString spanString2 = new SpannableString(tag  + " " +
                    (BuildConfig.DEBUG ? title+ "(" + id + ")" : title));
            spanString2.setSpan(new RoundBackgroundColorSpan(bg,textbg), 0,
                    tag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spanString2);
        }else{
            textView.setText(BuildConfig.DEBUG ? title + "(" +id + ")" : title);
        }
    }


    //设置 后缀文字
    public static void setRichText(Context context, String content, String suffix, TextView tv, int textg) {
        String result = content + suffix;
        SpannableString spannableString = new SpannableString(result);
        ForegroundColorSpan fCs1 = new ForegroundColorSpan(textg);
        //fCs1样式试用在start - end 之间
        spannableString.setSpan(fCs1,result.length() - suffix.length(),result.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);


        ClickableSpan user_ll = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                ((TextView)view).setHighlightColor(context.getResources().getColor(android.R.color.transparent));

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };

        spannableString.setSpan(user_ll, result.length() - suffix.length(),result.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //必须设置才能响应点击事件
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        tv.setText(spannableString);
    }

    //设置 后缀图片
    public static void setRichImg(Context context, String content, String suffix, TextView tv, int res_img) {

            String result = content + suffix;

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                }
            };
            //居中对齐imageSpan
            CustomImageSpan imageSpan = new CustomImageSpan(context,res_img,2);
            SpannableString spanString2 = new SpannableString(result);
            spanString2.setSpan(imageSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanString2.setSpan(clickableSpan,0,4,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv.append(spanString2);
            tv.setMovementMethod(LinkMovementMethod.getInstance());
            tv.setText(spanString2);
    }











}
