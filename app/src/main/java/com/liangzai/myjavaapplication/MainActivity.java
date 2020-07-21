package com.liangzai.myjavaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Button;

import com.liangzai.common_lib.StringUtils;
import com.liangzai.myjavaapplication.bean.HomeBean;
import com.liangzai.myjavaapplication.net.base.BaseObserver;
import com.liangzai.myjavaapplication.net.base.BaseWithoutCodeObserver;
import com.liangzai.myjavaapplication.net.helper.RetrofitHelper;
import com.liangzai.myjavaapplication.net.response.HttpResponse;
import com.liangzai.myjavaapplication.utils.Utils;
import com.socks.library.KLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Button test_timeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();

        initEvent();

    }

    private void initEvent() {
        test_timeout = findViewById(R.id.test_timeout);
        test_timeout.setOnClickListener(view -> {
            initTimeOut();

        });
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 8888:
                int value = (int) msg.obj;
                KLog.e("tag","当前值为 " + value);
                msg = Message.obtain();
                msg.what = 8888;
                msg.obj = value - 1;
                if(value > 0){
                    sendMessageDelayed(msg,1000);
                }
                break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    private void initTimeOut() {
//        Utils.initTimeOut();
        //第二种方式 Handler
//        Message message = Message.obtain();
//        message.obj = 10;
//        message.what = 8888;
//        mHandler.sendMessageDelayed(message,1000);
        //第三种方式 RxJava

    }

    //http://baobab.kaiyanapp.com/api/v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83
    private void getData() {
        RetrofitHelper.getApiService().getHomeData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new BaseWithoutCodeObserver<HomeBean>() {
                    @Override
                    public void onSuccess(HomeBean response) {
                        if(null != response){
                            for (HomeBean.IssueList issueList : response.getIssueList()) {
                                KLog.e("tag","第一条播放的数据是 " + response.getIssueList().get(0).getItemList().get(0).getType());
                            }
                        }
                    }

                    @Override
                    public void onHintError(String return_code, String errorMes) {

                    }
                });

    }
}