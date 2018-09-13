package rapid.com.rdframework;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import base.BaseIMActivity;
import base.BaseTIMActivity;
import rapid.com.rdframework.fragments.ModularFiveFragment;
import rapid.com.rdframework.fragments.ModularFourFragmentn;
import rapid.com.rdframework.fragments.ModularOneFragment;
import rapid.com.rdframework.fragments.ModularThreeFragment;
import rapid.com.rdframework.fragments.ModularsTwoFragment;

public class Main2Activity extends BaseTIMActivity {
    /**模块1*/
    ModularOneFragment oneFragment;
    /**模块2*/
    ModularsTwoFragment twoFragment;
    /**模块3*/
    ModularThreeFragment threeFragment;
    /**模块4*/
    ModularFourFragmentn fourFragmentn;
    /**模块5*/
    ModularFiveFragment fiveFragment;

    @Override
    public void initData(Context mContext) {
        //最后执行，做你自己的逻辑处理
    }

    @Override
    public void initParams() {
        //此方法会在绑定布局之前执行
        setSteepStatusBar(true);//是否沉浸状态栏，默认true
        setAllowFullScreen(true);//是否允许全屏，默认true
        setScreenRoate(false);//是否禁止旋转屏幕 false:禁止，true:不禁止
        //是否设置状态栏颜色,默认true,默认颜色：default_color：#b5b5b5
        setSetActionBarColor(true, rapid.com.base.R.color.default_color);
    }

    @Override
    public int[] selectImags() {
        //设置选中图标集合
        int[] imgsHovers = {
                R.mipmap.ic_home_actionbar_select0,
                R.mipmap.ic_home_actionbar_select1,
                R.mipmap.ic_home_actionbar_select2,
                R.mipmap.ic_home_actionbar_select3,
                R.mipmap.ic_home_actionbar_select4};
        return imgsHovers;
    }

    @Override
    public int[] unSelectImags() {
        //设置未选中图标集合
        int[] imageNormals = {
                R.mipmap.ic_home_actionbar0,
                R.mipmap.ic_home_actionbar1,
                R.mipmap.ic_home_actionbar2,
                R.mipmap.ic_home_actionbar3,
                R.mipmap.ic_home_actionbar4};
        return imageNormals;
    }

    @Override
    public int[] setTabTxt() {
         int[] tabTxts = {
                R.string.tabTxt1,
                R.string.tabTxt2,
                R.string.tabTxt3,
                R.string.tabTxt4,
                R.string.tabTxt5};
        return tabTxts;
    }

    //设置要绑定的模块
    @Override
    public List<Fragment> setFragments() {
        return getListFragments();//获取Fragment集合
    }

    private List<Fragment> getListFragments(){
        List<Fragment> list = new ArrayList<>();
        if (oneFragment == null) {
            oneFragment = new ModularOneFragment();
        }
        if (twoFragment == null) {
            twoFragment = new ModularsTwoFragment();
        }
        if (threeFragment == null) {
            threeFragment = new ModularThreeFragment();
        }
        if (fourFragmentn == null) {
            fourFragmentn = new ModularFourFragmentn();
        }
        if (fiveFragment == null) {
            fiveFragment = new ModularFiveFragment();
        }
        list.add(oneFragment);
        list.add(twoFragment);
        list.add(threeFragment);
        list.add(fourFragmentn);
//        list.add(fiveFragment);
        return list;
    }
}
