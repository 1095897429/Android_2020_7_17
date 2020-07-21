package com.liangzai.myjavaapplication.net.api;

import com.liangzai.myjavaapplication.bean.HomeBean;
import com.liangzai.myjavaapplication.net.base.BaseObserver;
import com.liangzai.myjavaapplication.net.response.HttpResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author zhouliang
 * 版本 1.0
 * 创建时间 2019-09-12
 * 描述:restful格式
 *
 */
public interface ApiEncryptService{

    //获取首页第一页数据
    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    Observable<HomeBean> getHomeData();
}
