package rapid.com.rdframework.fragments;

import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import base.BaseFragment;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.BuildConfig;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import rapid.com.rdframework.R;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by DN on 2017/9/6.
 */

public class ModularFiveFragment extends BaseFragment {

    @Override
    public int bindLayout() {
        return R.layout.modularfive_layout;
    }

    @Override
    protected void initView() {
    }
    public static String TAG = "Result";
    private static String url = "http://120.24.152.185/paintworld/getHomeData?appName=画世界&start=10&num=10&actionState=0&userID=100";
    @Override
    protected void initData() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).subscribeOn(Schedulers.newThread())//指定的是上游发送事件的线程
                //Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
                //Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
                //Schedulers.newThread() 代表一个常规的新线程
                //AndroidSchedulers.mainThread() 代表Android的主线程
           .observeOn(AndroidSchedulers.mainThread()) //指定的是下游接收事件的线程
           .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "subscribe");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
            }
        });

    }

    @Override
    public void widgetClick(View v) {
    }

    private static Retrofit create() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(9, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        return new Retrofit.Builder().baseUrl(url)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


}
