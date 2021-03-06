package base;

import android.os.Bundle;
import android.view.View;

import rapid.com.base.R;


/**
 * @author: FYL
 * @time: 2018/9/3
 * @email:347933430@qq.com
 * @describe: base相关
 */
public abstract class MenuFragmentActivity extends BaseFragmentActivity {
    private View[] tabRl;//菜单�?
    private View currentRl;//当前菜单�?
    private View lastRl;//上一个菜单项
    protected int currentIndex = 0;//索引
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //初始化tab
    protected void initTab(int[] tabResIds) {
        if (tabResIds == null || tabResIds.length <= 0) {
            return;
        }
        tabRl = new View[tabResIds.length];

        TabClickListener tabBtnListener = new TabClickListener();
        for (int i = 0; i < tabResIds.length; i++) {
            tabRl[i] = findViewById(tabResIds[i]);
            tabRl[i].setOnClickListener(tabBtnListener);
        }
        currentRl = tabRl[currentIndex];
    }
    //初始化tab
    protected void initTab(View view, int[] tabResIds) {
        if (view == null || tabResIds == null || tabResIds.length <= 0) {
            return;
        }
        tabRl = new View[tabResIds.length];

        TabClickListener tabBtnListener = new TabClickListener();
        for (int i = 0; i < tabResIds.length; i++) {
            tabRl[i] = view.findViewById(tabResIds[i]);
            tabRl[i].setOnClickListener(tabBtnListener);
        }
        currentRl = tabRl[currentIndex];
    }

    private class TabClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v == lastRl) {
                return;
            }
            boolean result = onTabClick(v.getId());
            setTabView(v, result);
        }
    }
    protected boolean onTabClick(int tabId) {
        return true;
    }
    //初始化背�?
    private View[] bg;
    protected void initTabBg(View view, int[] tabResIds) {
        if (view == null || tabResIds == null || tabResIds.length <= 0) {
            return;
        }
        bg = new View[tabResIds.length];
        for (int i = 0; i < tabResIds.length; i++) {
            bg[i] = view.findViewById(tabResIds[i]);
        }
    }

    protected void initTabBg(int[] tabResIds) {
        if (tabResIds == null || tabResIds.length <= 0) {
            return;
        }
        bg = new View[tabResIds.length];
        for (int i = 0; i < tabResIds.length; i++) {
            bg[i] = findViewById(tabResIds[i]);
            if (i == 0) {
                bg[i].setBackgroundColor(getResources().getColor(R.color.default_color));
            } else {
                bg[i].setBackgroundColor(getResources().getColor(R.color.ts_0));
            }
        }
    }
    private void setTabView(View v, boolean result) {
        if (!result) {
            return;
        }
        currentRl = v;
        lastRl = currentRl;
        boolean hasBg = false;
        if (bg != null && tabRl.length == bg.length) {
            hasBg = true;
        }
        for (int i = 0; i < tabRl.length; i++) {
            if (currentRl == tabRl[i]) {
                currentIndex = i;
                if (hasBg) {
                    bg[i].setBackgroundColor(getResources().getColor(R.color.default_color));
                }
            } else {
                tabRl[i].setBackgroundColor(getResources().getColor(R.color.ts_0));
                if (hasBg) {
                    bg[i].setBackgroundColor(getResources().getColor(R.color.ts_0));
                }
            }
        }
    }
}
