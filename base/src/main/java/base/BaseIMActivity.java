package base;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import rapid.com.base.R;


/**
 * @author: FYL
 * @time: 2018/9/6
 * @email:347933430@qq.com
 * @describe: m.b.base
 */
public abstract class BaseIMActivity extends MenuFragmentActivity {
    private int flResId = R.id.fl_menu_container;
    private int[] tabResIds = { R.id.iv_menu_0, R.id.iv_menu_1,R.id.iv_menu_2, R.id.iv_menu_3, R.id.iv_menu_4 };
    private int[] imgIds = { R.id.iv_menu_0, R.id.iv_menu_1, R.id.iv_menu_2,R.id.iv_menu_3,R.id.iv_menu_4};
    private ImageView[] imgBtn = new ImageView[imgIds.length];
    private int[] imageNormals;//未选中图标集合
    private int[] imgsHovers;//选中图标集合
    private List<Fragment> fragments; //页面集合
    private LinearLayout main_menu_table;
    @Override
    public int bindLayout() {
        initParams();
        return R.layout.base_img_main_activity;
    }
    public abstract void initParams();
    @Override
    public void initView(View view) {
        super.initTab(tabResIds);
        main_menu_table = findViewById(R.id.main_menu_table);
        imgsHovers = selectImags();
        imageNormals = unSelectImags();
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
            imgBtn[i].setVisibility(View.VISIBLE);
        }
//        setTabBackgroundColor(tabColor);//设置默认的tab背景颜色
        imgBtn[0].setImageResource(imgsHovers[0]);
        //首次加载第一个Fragment
        switchFragment(flResId, fragments.get(0));
    }
    public abstract int[] selectImags();//图标
    public abstract int[] unSelectImags();//未选中图标
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
        }
        super.onTabClick(tabId);
        if(tabId==imgIds[0]){
            imgBtn[0].setImageResource(imgsHovers[0]);
            switchFragment(flResId, fragments.get(0));
        }else if(tabId==imgIds[1]){
            imgBtn[1].setImageResource(imgsHovers[1]);
            switchFragment(flResId, fragments.get(1));
        }else if(tabId==imgIds[2]){
            imgBtn[2].setImageResource(imgsHovers[2]);
            switchFragment(flResId, fragments.get(2));
        }else if(tabId==imgIds[3]){
            imgBtn[3].setImageResource(imgsHovers[3]);
            switchFragment(flResId, fragments.get(3));
        }else if(tabId==imgIds[4]){
            imgBtn[4].setImageResource(imgsHovers[4]);
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
