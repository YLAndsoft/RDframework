package rapid.com.rdframework.utilsdemo;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.BaseActivity;
import rapid.com.rdframework.R;
import rapid.com.tools.EventBusEvent;
import rapid.com.tools.EventBusTools;
import rapid.com.tools.OtherUtils;
import rapid.com.tools.SpanUtils;

/**
 * @author: FYL
 * @time: 2018/9/25
 * @email:347933430@qq.com
 * @describe: rapid.com.rdframework.utilsdemo
 */
public class EventBusActivity extends BaseActivity {
    @ViewInject(value = R.id.eventbus_txt)
    private TextView eventbus_txt;
    @Override
    public int bindLayout() {
        return R.layout.eventbus_utils_activity;
    }

    @Override
    public void initView(View view) {
        x.view().inject(this);
        eventbus_txt.setText(UtilsConstant.eventbus);
    }

    /**使用：*/
    @Override
    public void initListener() {
        //在onCreat()进行注册
        //EventBusTools.register(this);//注册广播
    }

    @Override
    public void initData(Context mContext) {
       //发送普通广播
       // EventBusTools.sendEvent(new EventBusEvent(0));
       //发送粘性广播
      //EventBusTools.sendStickyEvent(new EventBusEvent(0));
    }

    /**接收普通广播*/
    @Subscribe(threadMode= ThreadMode.MAIN)
    public void onEventMessage(EventBusEvent event) {
        //做自己的逻辑处理
    }

    /**接收粘性广播*/
    @Subscribe(threadMode= ThreadMode.MAIN,sticky=true)
    public void onStickyEventMessage(EventBusEvent event) {
        //做自己的逻辑处理
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播,建议在onDestroy()方法调用
        //EventBusTools.unregister(this);
    }


    @Override
    public void widgetClick(View v) {
    }



}
