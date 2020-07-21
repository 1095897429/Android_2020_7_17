package com.liangzai.common_lib;

import android.app.Application;
import android.content.Context;

import com.socks.library.KLog;

/**
 * @author zhouliang
 * 版本 1.0
 * 创建时间 2020/7/20
 * 描述:
 */
public class CommonApplication extends Application {

    private static CommonApplication myApp;

    /** 获取全局唯一上下文 */
    public static CommonApplication getApplication() {
        return myApp;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        myApp = this;
    }
}
