package rapid.com.rdframework.utilsdemo;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.BaseActivity;
import rapid.com.rdframework.R;

/**
 * @author: FYL
 * @time: 2018/9/25
 * @email:347933430@qq.com
 * @describe: rapid.com.rdframework.utilsdemo
 */
public class ComparableActivity extends BaseActivity {
    @ViewInject(value = R.id.comparable_txt)
    private TextView comparable_txt;
    @Override
    public int bindLayout() {
        return R.layout.comparable_utils_activity;
    }
    @Override
    public void initView(View view) {
        x.view().inject(this);
    }
    @Override
    public void initListener() {
    }
    @Override
    public void initData(Context mContext) {
        comparable_txt.setText(UtilsConstant.compaprablel);
    }

    @Override
    public void widgetClick(View v) {

    }
}
