package com.liangzai.common_lib;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

/**
 * @author zhouliang
 * 版本 1.0
 * 创建时间 2020/7/20
 * 描述:
 */
public class StringUtils {


    public static void showCenterToast(String text){
        ToastUtils.setGravity(Gravity.CENTER,0,0);
        View view = LayoutInflater.from(CommonApplication.getApplication()).inflate(R.layout.toast_common,null);
        ((TextView)view.findViewById(R.id.title_1)).setText(text);
        ToastUtils.showCustomShort(view);
    }
}
