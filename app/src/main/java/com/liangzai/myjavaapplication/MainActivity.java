package com.liangzai.myjavaapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import android.os.Bundle;
import android.text.TextUtils;

import com.liangzai.common_lib.StringUtils;
import com.liangzai.myjavaapplication.bean.HomeBean;
import com.liangzai.myjavaapplication.net.base.BaseObserver;
import com.liangzai.myjavaapplication.net.base.BaseWithoutCodeObserver;
import com.liangzai.myjavaapplication.net.helper.RetrofitHelper;
import com.liangzai.myjavaapplication.net.response.HttpResponse;
import com.socks.library.KLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();

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