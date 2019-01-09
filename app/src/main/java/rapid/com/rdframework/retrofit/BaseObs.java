package rapid.com.rdframework.retrofit;

import android.accounts.NetworkErrorException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author: FYL
 * @time: 2018/10/18
 * @email:347933430@qq.com
 * @describe: rapid.com.rdframework.retrofit
 */
public abstract class BaseObs<T> implements Observer<BaseEntity<T>>{


    @Override
    public void onNext(BaseEntity t) {
        if (t.isSuccess()) {
            try {
                onSuccees(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                onCodeError(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }
    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    public abstract void onSuccees(BaseEntity<T> t) throws Exception;
    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    public void onCodeError(BaseEntity<T> t) throws Exception {
    }
    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    public abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;
}
