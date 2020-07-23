package com.liangzai.myjavaapplication.utils;

import android.content.Context;
import android.content.Intent;

import com.liangzai.myjavaapplication.activity.VideoListActivity;

/**
 * @author zhouliang
 * 版本 1.0
 * 创建时间 2020/7/22
 * 描述:
 */
public class UIHelper {

    public static void toVideoListActivity(Context ctx) {
        Intent intent = new Intent(ctx, VideoListActivity.class);
        ctx.startActivity(intent);
    }

}
