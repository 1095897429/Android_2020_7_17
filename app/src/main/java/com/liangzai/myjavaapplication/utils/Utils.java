package com.liangzai.myjavaapplication.utils;

import android.os.CountDownTimer;

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













}
