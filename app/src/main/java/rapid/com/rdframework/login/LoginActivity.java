package rapid.com.rdframework.login;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.BaseActivity;
import rapid.com.db.SPManager;
import rapid.com.rdframework.R;
import rapid.com.rdframework.SelectActivity;

/**
 * @author: FYL
 * @time: 2018/9/14
 * @email:347933430@qq.com
 * @describe: rapid.com.rdframework.login
 */
public class LoginActivity extends BaseActivity {

    @ViewInject(value = R.id.edit_account)
    private EditText edit_account;
    @ViewInject(value = R.id.edit_password)
    private EditText edit_password;
    @ViewInject(value = R.id.login)
    private Button login;
    @ViewInject(value = R.id.checkBox)
    private CheckBox checkBox;

    private boolean isChecked;
    private  String dbpassword="";
    private  String dbaccount="";
    @Override
    public int bindLayout() {
        boolean isLogin = (boolean) SPManager.getInstance(mContext).getValue("isLogin", false);
        if(isLogin){
            toNext(SelectActivity.class);
            finish();
        }
        return R.layout.login_layout;
    }

    @Override
    public void initView(View view) {
        x.view().inject(this);
    }

    @Override
    public void initListener() {
        login.setOnClickListener(this);
    }

    @Override
    public void initData(Context mContext) {
        dbaccount = (String) SPManager.getInstance(mContext).getValue("account", "");
        dbpassword = (String) SPManager.getInstance(mContext).getValue("password", "");
        if("".equals(dbaccount)){
            edit_account.setHint("请输入账号");
        }else{
            edit_account.setText(dbaccount);
        }
        if("".equals(dbpassword)){
            edit_password.setHint("请输入密码");
        }
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.login:
                logins();
                break;
        }
    }

    private void logins() {
        String account = edit_account.getText().toString().trim();
        String password = edit_password.getText().toString().trim();
        if("".equals(account)){
            showToast("账号不能为空!");
            return;
        }
        if("".equals(password)){
            showToast("密码不能为空!");
            return;
        }
        if(dbaccount!=null&&!"".equals(dbaccount)){
            if(!dbaccount.equals(account)){
                showToast("账号错误!");
                return;
            }
        }
        if(dbpassword!=null&&!"".equals(dbpassword)){
            if(!dbpassword.equals(password)){
                showToast("密码错误!");
                return;
            }
        }
        SPManager.getInstance(mContext).setValue("account",account);//保存账号
        SPManager.getInstance(mContext).setValue("password",password);//保存密码
        isChecked=checkBox.isChecked();

        SPManager.getInstance(mContext).setValue("isLogin",isChecked);//保存是否记录登录状态
        toNext(SelectActivity.class);
        finish();

    }

}
