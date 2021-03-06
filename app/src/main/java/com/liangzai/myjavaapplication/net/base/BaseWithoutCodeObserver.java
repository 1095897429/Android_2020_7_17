package com.liangzai.myjavaapplication.net.base;

import com.google.gson.JsonParseException;
import com.liangzai.common_lib.StringUtils;
import com.liangzai.myjavaapplication.net.response.HttpResponse;
import com.socks.library.KLog;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/***
 * 把整体数据送进来检查一下，然后再送出去
 * @param <T>
 */
public abstract class BaseWithoutCodeObserver<T> extends DisposableObserver<T> {


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(T response) {
        try {
            onSuccess(response);
        }catch (Exception e){

        }

    }


    @Override
    public void onError(Throwable t) {

        KLog.d("错误❎的信息：" + t.getMessage());

        try {
            if(t instanceof HttpException){
                //   HTTP错误
                onException(ExceptionReason.BAD_NETWORK);
            }else if(t instanceof ConnectException
                    || t instanceof UnknownHostException){
                //  连接错误
                onException(ExceptionReason.CONNECT_ERROR);
            }else if(t instanceof InterruptedException){
                //  连接超时
                onException(ExceptionReason.CONNECT_TIMEOUT);
            } else if(t instanceof JsonParseException
                    || t instanceof JSONException
                    || t instanceof ParseException){
                //  解析错误
                onException(ExceptionReason.PARSE_ERROR);
            }else if(t instanceof SocketException){
                //  服务器响应超时
                onException(ExceptionReason.RESPONSE_TIMEOUT);
            }else{
                //未知错误
                onException(ExceptionReason.UNKNOWN_ERROR);
            }
        }catch (Exception e){

        }

    }


    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                onNetFail("连接超时");
                break;

            case CONNECT_TIMEOUT:
                KLog.d("连接错误");
                onNetFail("连接错误");
                break;

            case BAD_NETWORK:
                KLog.d("HTTP错误");
                onNetFail("HTTP错误");
                break;

            case PARSE_ERROR:
                KLog.d("解析错误 不给予 toast 提示");
                onNetFail("解析错误");
                break;

            case UNKNOWN_ERROR:
                KLog.d("未知错误 不给予 toast 提示");
                onNetFail("未知错误");

            default:
                break;
        }
    }

    /** --------------------- 枚举 -------------------- */

    public enum ExceptionReason {
        /** 解析数据失败 */
        PARSE_ERROR,
        /** 网络问题 */
        BAD_NETWORK,
        /** 连接错误 */
        CONNECT_ERROR,
        /** 连接超时 */
        CONNECT_TIMEOUT,
        /** 响应超时 */
        RESPONSE_TIMEOUT,
        /** 未知错误 */
        UNKNOWN_ERROR,
    }


    @Override
    public void onComplete() {

    }

    //自定义抽象方法 成功 提示错误 链接错误
    public abstract void onSuccess(T t);

    //如果code 不是200 ,则toast显示
    public void onHintError(String return_code, String errorMes){
        StringUtils.showCenterToast(errorMes);
    }

    //网络请求过程错误 toast提示
    public void onNetFail(String msg){
        if(!"未知错误".equals(msg) &&
            !"解析错误".equals(msg)){
            StringUtils.showCenterToast(msg);
        }

    }




}
