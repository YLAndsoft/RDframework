package rapid.com.rdframework;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.BaseActivity;

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
    @ViewInject(value = R.id.rg_btn)
    private RadioGroup rg_btn;
    @ViewInject(value = R.id.commit)
    private Button commit;
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
        rg_btn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton)SelectActivity.this.findViewById(radioGroup.getCheckedRadioButtonId());
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ischeck1 = rb_btn1.isChecked();
                boolean ischeck2 = rb_btn2.isChecked();
                if(ischeck1){
                    startActivity(new Intent(mContext,MainActivity.class));
                }
                if(ischeck2){
                    startActivity(new Intent(mContext,Main2Activity.class));
                }

            }
        });
    }

    @Override
    public void initData(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {

    }
}
