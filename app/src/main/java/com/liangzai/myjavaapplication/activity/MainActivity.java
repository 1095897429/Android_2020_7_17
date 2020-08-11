package com.liangzai.myjavaapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.badoo.mobile.util.WeakHandler;
import com.facebook.fbui.textlayoutbuilder.TextLayoutBuilder;
import com.gyf.immersionbar.ImmersionBar;
import com.liangzai.common_lib.StringUtils;
import com.liangzai.myjavaapplication.R;
import com.liangzai.myjavaapplication.bean.HomeBean;
import com.liangzai.myjavaapplication.net.base.BaseObserver;
import com.liangzai.myjavaapplication.net.base.BaseWithoutCodeObserver;
import com.liangzai.myjavaapplication.net.helper.RetrofitHelper;
import com.liangzai.myjavaapplication.net.response.HttpResponse;
import com.liangzai.myjavaapplication.utils.UIHelper;
import com.liangzai.myjavaapplication.utils.Utils;
import com.liangzai.myjavaapplication.widget.CustomImageSpan;
import com.socks.library.KLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.vstechlab.easyfonts.EasyFonts;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("HandlerLeak")
    private Handler mWeakHandler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    private Button test_timeout;

    private TextView title;
    private TextView tag_text;
    private TextView suffix_text;
    private TextView suffix_text_2;

    private ImmersionBar mImmersionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mImmersionBar = ImmersionBar.with(this);//初始化
        mImmersionBar
                .statusBarDarkFont(true, 0.2f)//设置状态栏图片为深色，(如果android 6.0以下就是半透明)
                .fitsSystemWindows(true)//设置这个是为了防止布局和顶部的状态栏重叠
                .statusBarColor(R.color.white)//这里的颜色，你可以自定义。
                .init();


        title = findViewById(R.id.title);
        title.setTypeface(EasyFonts.robotoMedium(this));

        tag_text = findViewById(R.id.tag_text);
        Utils.setTitleTag("直播回放","如果通过反射来创建新的实例，可以调用Class提供的newInstance()方法：",
                "12",tag_text
                ,getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.text_first_color));

        suffix_text = findViewById(R.id.suffix_text);

        String richText = "点击“确认支付”则代表您已经知悉并同意";
        String suffixText = "《鸟哥笔记用户协议》";
        Utils.setRichText(getApplicationContext(),richText,suffixText,suffix_text, Color.parseColor("#FFBA20"));

        suffix_text_2 = findViewById(R.id.suffix_text_2);

        //拼接链接
        String link = "https://www.baidu.com";
        if(!TextUtils.isEmpty(link)){
            Utils.setRichImg(getApplicationContext(),"我是link测试的哈，小番薯","icon",suffix_text_2, R.mipmap.icon_flash_link);
        }

        getData();

        initEvent();

    }

    private void initEvent() {
        test_timeout = findViewById(R.id.test_timeout);
        test_timeout.setOnClickListener(view -> {
//            initTimeOut();
            UIHelper.toVideoListActivity(this);
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
        KLog.e("tag","界面销毁");
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
        //返回的类型是call
        Call call  = RetrofitHelper.getApiService().getHomeDataCall();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

        //返回的类型是observable
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


        //① 测试
        Observable<String> observable;
        //可以简单的看成是Observable.create(new 接口对象);
        observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                System.out.println("服务员从厨师那取得 扁食");
                e.onNext("扁食");
                System.out.println("服务员从厨师那取得 拌面");
                e.onNext("拌面");
                System.out.println("服务员从厨师那取得 蒸饺");
                e.onNext("蒸饺");
                System.out.println("厨师告知服务员菜上好了");
                e.onComplete();
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("来个沙县套餐！！！");
            }

            @Override
            public void onNext(String s) {
                System.out.println("服务员端给顾客  " + s);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                System.out.println("服务员告诉顾客菜上好了");
            }
        };

        observable.subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        KLog.e("tag","code " + keyCode);
        if(keyCode == KeyEvent.KEYCODE_BACK){

            UIHelper.toVideoListActivity(this);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




}