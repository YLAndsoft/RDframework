package rapid.com.rdframework.fragments;

import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import base.BaseFragment;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rapid.com.rdframework.R;
import rapid.com.rdframework.retrofit.BaseEntity;
import rapid.com.rdframework.retrofit.BaseObs;
import rapid.com.rdframework.retrofit.BaseObserver;
import rapid.com.rdframework.retrofit.RetrofitFactory;
import rapid.com.rdframework.retrofit.SearchData;


/**
 * Created by DN on 2017/9/6.
 */

public class ModularFourFragmentn extends BaseFragment {

    @ViewInject(value = R.id.recycler)
    RecyclerView recycler;
    @Override
    public int bindLayout() {
        return R.layout.modularfour_layout;
    }

    @Override
    protected void initView() {
        x.view().inject(this,mContextView);
    }

    @Override
    protected void initData() {
        Map<String,String> map = new HashMap<>();
        map.put("appName","画世界");
        map.put("start","10");
        map.put("num","10");
        map.put("actionState","0");
        map.put("userID","100");
        RetrofitFactory.getInstence().API()
                .getData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("TAG",d.isDisposed()+"");
                    }
                    @Override
                    public void onNext(SearchData searchData) {
                        Log.i("TAG",searchData.getWorks().get(0).getUserName());
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAG",e.getMessage());
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void widgetClick(View v) {

    }
}
