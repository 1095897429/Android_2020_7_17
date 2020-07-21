package com.liangzai.myjavaapplication.net.helper;

import com.blankj.utilcode.util.SPUtils;
import com.liangzai.myjavaapplication.BuildConfig;
import com.liangzai.myjavaapplication.net.api.ApiEncryptService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author zhouliang
 * 版本 1.0
 * 创建时间 2019-07-03
 * 描述:网络请求管理类
 */
public class RetrofitHelper {

    //组合Retrofit
    private Retrofit mRetrofit;
    //单例
    private static RetrofitHelper instance;
    //接口地址
    private static ApiEncryptService mApiService;
    //端口地址
    public static String BASEURL = "http://mitao.birdbrowser.ifo/";
    private static final String BASE_URL_USER = "http://www.111.com/";
    private static final String BASE_URL_PAY = "http://www.222.com/";
    //加密参数
    private static final String AES_KEY = "werojcllljowerl3905uLDZ90t20jsdf";
    private static final String AES_IV = "0123456789ABCDEF";


    private RetrofitHelper(){}

    public static RetrofitHelper getInstance(){
        if(null == instance){
            synchronized (RetrofitHelper.class){
                if(null == instance){
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    //获取Retrofit
    public Retrofit getRetrofit(){

        if(BuildConfig.DEBUG){
            //TODO 6.11 环境切换
            String url ;
            if(SPUtils.getInstance().getBoolean("login_enventment",false)){
                url = "http://baobab.kaiyanapp.com/api/";
            }else{
                url = "http://baobab.kaiyanapp.com/api/";
            }
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(OkHttpHelper.getInstance().getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            mRetrofit = builder.build();
        }else{
            if(null == mRetrofit){
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(BuildConfig.URL)
                        .client(OkHttpHelper.getInstance().getOkHttpClient())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
                mRetrofit = builder.build();
            }
        }

        return mRetrofit;
    }


    //获取service的代理对象
    public static ApiEncryptService getApiService(){
        if(BuildConfig.DEBUG){
            mApiService = getInstance().getRetrofit().create(ApiEncryptService.class);
        }else{
            if(null == mApiService){
                mApiService = getInstance().getRetrofit().create(ApiEncryptService.class);
              }
        }



        return mApiService;
    }


    //配置公共参数 -- 加密
    public static String commonParam(java.util.Map<String, String> map) {
        return null;

    }


}
