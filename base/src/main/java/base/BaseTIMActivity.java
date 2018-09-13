package base;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import rapid.com.base.R;


/**
 * @author: FYL
 * @time: 2018/9/6
 * @email:347933430@qq.com
 * @describe: m.b.base
 */
public abstract class BaseTIMActivity extends MenuFragmentActivity {
    private int flResId = R.id.fl_menu_container;
    private int[] tabResIds = {R.id.rl_menu_1,R.id.rl_menu_2, R.id.rl_menu_3, R.id.rl_menu_4,R.id.rl_menu_5 };
    private int[] imgIds = {R.id.iv_menu_1, R.id.iv_menu_2,R.id.iv_menu_3,R.id.iv_menu_4,R.id.iv_menu_5};
    private int[] txtIds = {R.id.tv_menu_1, R.id.tv_menu_2,R.id.tv_menu_3,R.id.tv_menu_4,R.id.tv_menu_5};
    private ImageView[] imgBtn = new ImageView[imgIds.length];
    private TextView[] txtBtn = new TextView[txtIds.length];
    private RelativeLayout[] rlayouts = new RelativeLayout[tabResIds.length];
    private int[] imageNormals;//未选中图标集合
    private int[] imgsHovers;//选中图标集合
    private int[] tabTxts;//选中图标集合
    private List<Fragment> fragments; //页面集合
    private LinearLayout main_menu_table;
    @Override
    public int bindLayout() {
        initParams();
        return R.layout.base_txt_img_main_activity;
    }
    public abstract void initParams();
    @Override
    public void initView(View view) {
        super.initTab(tabResIds);
        main_menu_table = findViewById(R.id.main_menu_table);
        imgsHovers = selectImags();
        imageNormals = unSelectImags();
        tabTxts = setTabTxt();

        fragments = setFragments();
        if(null==fragments||fragments.size()<4){
            showLog(3,"tab对应的界面不能少于4个");
            return;
        }
        if(null==imgsHovers||imgsHovers.length<4){
            showLog(3,"选中图标不能少于4个");
            return;
        }
        if(null==imageNormals||imageNormals.length<4){
            showLog(3,"未选中图标不能少于4个");
            return;
        }
        for (int i = 0; i < fragments.size(); i++) {
            imgBtn[i] = (ImageView) findViewById(imgIds[i]);
            imgBtn[i].setImageResource(imageNormals[i]);

            txtBtn[i] = (TextView) findViewById(txtIds[i]);
            txtBtn[i].setText(tabTxts[i]);

            rlayouts[i] = (RelativeLayout) findViewById(tabResIds[i]);
            rlayouts[i].setVisibility(View.VISIBLE);

        }
        imgBtn[0].setImageResource(imgsHovers[0]);
        txtBtn[0].setTextColor(getResources().getColor(R.color.default_select_color));
        //首次加载第一个Fragment
        switchFragment(flResId, fragments.get(0));


    }
    public abstract int[] selectImags();//图标
    public abstract int[] unSelectImags();//未选中图标
    public abstract int[] setTabTxt();//tab文字
    public abstract List<Fragment> setFragments();//Fragment集合

    private long mkeyTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                mkeyTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected boolean onTabClick(int tabId) {
        for (int i = 0; i < fragments.size(); i++) {
            imgBtn[i].setImageResource(imageNormals[i]);
            txtBtn[i].setTextColor(getResources().getColor(R.color.default_color));//default_select_color
        }
        super.onTabClick(tabId);
        if(tabId==tabResIds[0]){
            imgBtn[0].setImageResource(imgsHovers[0]);
            txtBtn[0].setTextColor(getResources().getColor(R.color.default_select_color));
            switchFragment(flResId, fragments.get(0));
        }else if(tabId==tabResIds[1]){
            imgBtn[1].setImageResource(imgsHovers[1]);
            txtBtn[1].setTextColor(getResources().getColor(R.color.default_select_color));
            switchFragment(flResId, fragments.get(1));
        }else if(tabId==tabResIds[2]){
            imgBtn[2].setImageResource(imgsHovers[2]);
            txtBtn[2].setTextColor(getResources().getColor(R.color.default_select_color));
            switchFragment(flResId, fragments.get(2));
        }else if(tabId==tabResIds[3]){
            imgBtn[3].setImageResource(imgsHovers[3]);
            txtBtn[3].setTextColor(getResources().getColor(R.color.default_select_color));
            switchFragment(flResId, fragments.get(3));
        }else if(tabId==tabResIds[4]){
            imgBtn[4].setImageResource(imgsHovers[4]);
            txtBtn[4].setTextColor(getResources().getColor(R.color.default_select_color));
            switchFragment(flResId, fragments.get(4));
        }
        return true;
    }
    @Override
    public void initListener() {
    }

    @Override
    public void widgetClick(View v) {
    }

    /**
     * //设置tab背景颜色
     * @param color
     */
    public void setTabBackgroundColor(int color){
        if(main_menu_table!=null){
            main_menu_table.setBackgroundColor(color);
        }
    }

    /**
     * 设置tab背景资源
     * @param resid
     */
    public void setTabBackgroundResource(int resid){
        if(main_menu_table!=null){
            main_menu_table.setBackgroundResource(resid);
        }
    }

    /**
     * 设置tab背景drawable
     * @param drawable
     */
    public void setTabBackground(Drawable drawable){
        if(main_menu_table!=null){
            main_menu_table.setBackground(drawable);
        }
    }

}
