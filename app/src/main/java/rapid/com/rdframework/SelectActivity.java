package rapid.com.rdframework;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.BaseActivity;
import rapid.com.db.SPManager;
import rapid.com.rdframework.dbdemo.SQLiteActivity;
import rapid.com.rdframework.dbdemo.SharedActivity;
import rapid.com.rdframework.login.LoginActivity;
import rapid.com.rdframework.utilsdemo.UtilsDemoActivity;
import vUtils.ViewUtils;

/**
 * @author: FYL
 * @time: 2018/9/13
 * @email:347933430@qq.com
 * @describe: rapid.com.rdframework
 */
public class SelectActivity extends BaseActivity {

    @ViewInject(value = R.id.rb_btn1)
    private RadioButton rb_btn1;
    @ViewInject(value = R.id.rb_btn2)
    private  RadioButton rb_btn2;
    @ViewInject(value = R.id.commit)
    private Button commit;
    @ViewInject(value = R.id.out_login)
    private TextView out_login;
    @ViewInject(value = R.id.rb_btn3)
    private RadioButton rb_btn3;
    @ViewInject(value = R.id.commit1)
    private Button commit1;
    @ViewInject(value = R.id.commit2)
    private Button commit2;
    @Override
    public int bindLayout() {
        return R.layout.select_activity;
    }

    @Override
    public void initView(View view) {
        x.view().inject(this);
    }

    @Override
    public void initListener() {
        commit.setOnClickListener(this);
        commit1.setOnClickListener(this);
        commit2.setOnClickListener(this);
        out_login.setOnClickListener(this);
    }

    @Override
    public void initData(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.commit:
                boolean ischeck1 = rb_btn1.isChecked();
                boolean ischeck2 = rb_btn2.isChecked();
                if(ischeck1){
                    startActivity(new Intent(mContext,MainActivity.class));
                }
                if(ischeck2){
                    startActivity(new Intent(mContext,Main2Activity.class));
                }
                break;
            case R.id.commit1:
                boolean ischeck3 = rb_btn3.isChecked();
                if(ischeck3){
                    startActivity(new Intent(SelectActivity.this, SQLiteActivity.class));
                }
                break;
            case R.id.commit2:
                ViewUtils.startActivity(this, UtilsDemoActivity.class);
                break;
            case R.id.out_login:
                SPManager.getInstance(mContext).setValue("isLogin",false);//保存是否记录登录状态
                startActivity(new Intent(SelectActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }






}
