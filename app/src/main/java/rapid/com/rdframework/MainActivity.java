package rapid.com.rdframework;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import base.BaseMainActivity;
import rapid.com.rdframework.fragments.ModularFiveFragment;
import rapid.com.rdframework.fragments.ModularFourFragmentn;
import rapid.com.rdframework.fragments.ModularsTwoFragment;
import rapid.com.rdframework.fragments.ModularThreeFragment;
import rapid.com.rdframework.fragments.ModularOneFragment;

public class MainActivity extends BaseMainActivity {
    /**模块1*/
    ModularsTwoFragment twoFragment;
    /**模块2*/
    ModularOneFragment oneFragment;
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
        setSetActionBarColor(true, rapid.com.base.R.color.ts_0);
    }

    @Override
    public int[] selectImags() {
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
        int[] imageNormals = {
                R.mipmap.ic_home_actionbar0,
                R.mipmap.ic_home_actionbar1,
                R.mipmap.ic_home_actionbar2,
                R.mipmap.ic_home_actionbar3,
                R.mipmap.ic_home_actionbar4};
        return imageNormals;
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
