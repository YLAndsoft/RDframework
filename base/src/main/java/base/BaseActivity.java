package base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import rapid.com.base.R;


/**
 * @author: FYL
 * @time: 2018/9/3
 * @email:347933430@qq.com
 * @describe: base相关
 */

public abstract class BaseActivity extends Activity implements View.OnClickListener{

    /** 是否沉浸状态栏,默认true**/
    private boolean isSetStatusBar = true;
    /** 是否允许全屏,默认true **/
    private boolean mAllowFullScreen = true;
    /** 是否设置状态栏颜色,默认true*/
    private boolean isSetActionBarColor = true;
    /** 是否禁止旋转屏幕 false:禁止，true:不禁止,默认false**/
    private boolean isAllowScreenRoate = false;
    /** 当前Activity渲染的视图View **/
    protected View mContextView = null;

    /**上文*/
    protected Context mContext;
    /**默认状态栏的颜色为透明*/
    private int mResColor = R.color.ts_0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        try{
            mContextView = LayoutInflater.from(mContext).inflate(bindLayout(), null);
        }catch (Exception ex){
            showToast("布局加载异常！");
            return;
        }
        if (mAllowFullScreen)requestWindowFeature(Window.FEATURE_NO_TITLE);//设置全屏
        if (isSetStatusBar)steepStatusBar();//设置沉淀式状态栏
        if(isSetActionBarColor)setActionBarColor(mResColor);//设置通知栏的颜色
        if (!isAllowScreenRoate)setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //禁止屏幕旋转
        setContentView(mContextView); //设置布局
        initView(mContextView);
        initListener();
        initData(this);
    }
    /**
     * 绑定布局
     * @return
     */
    public abstract int bindLayout();

    /**
     * [初始化控件]
     * @param view
     */
    public abstract void initView(final View view);

    /**
     * [设置监听]
     */
    public abstract void initListener();

    /**
     * [业务操作]
     * @param mContext
     */
    public abstract void initData(Context mContext);

    /** View点击
     * @param v
     */
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v){
        widgetClick(v);
    }
    /**
     * 返回，关闭界面
     * @param v
     */
    public void toBack(View v)
    {
        finish();
    }

    public void toNext(Class<?> clazz){
        startActivity(new Intent(mContext,clazz));
    }

    public void toFinishNext(Class<?> clazz){
        startActivity(new Intent(mContext,clazz));
        finish();
    }

    private void setActionBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);//通知栏颜色
        }
    }

    /**
     * [沉浸状�?�栏]
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 隐藏软件盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showInputMethod(){
        if (getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),0);
        }
    }

    /**
     * 点击软键盘之外的空白处，隐藏软件盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) return true;
        return onTouchEvent(ev);
    }
    /**
     * Return whether touch the view.判断点击是否是EditText区域
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],top = l[1],bottom = top + v.getHeight(),right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right&& event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    /**
     * [吐出Toast]
     * @param msg
     */
    protected void showToast(String msg){
            if(null!=getApplicationContext()){
                Toast.makeText(mContext,msg, Toast.LENGTH_SHORT).show();
            }
    }

    /**
     * [打印日志]
     * @param msg
     */
    protected final String TAG = "ActivityLog信息：";
    protected void showLog(int level,String msg){
        if(!BaseConfig.isLog){return;}
        switch (level){
            case BaseConfig.LEVEL_1:
                Log.v(TAG,msg);
                break;
            case BaseConfig.LEVEL_2:
                Log.d(TAG,msg);
                break;
            case BaseConfig.LEVEL_3:
                Log.e(TAG,msg);
                break;
            default:
                Log.i(TAG,msg);
                break;
        }
    }


    /**
     * [是否允许全屏]
     *
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /**
     * [是否设置沉浸状态栏]
     *
     * @param isSetStatusBar
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }

    /**
     * [是否允许屏幕旋转]
     *
     * @param isAllowScreenRoate
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

    public void setSetActionBarColor(boolean isSetActionBarColor,int resColor) {
        this.isSetActionBarColor = isSetActionBarColor;
        this.mResColor = resColor;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
