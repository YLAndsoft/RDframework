package rapid.com.rdframework.utilsdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import adapter.BaseRecyclerAdapter;
import adapter.BaseRecyclerHolder;
import base.BaseActivity;
import rapid.com.rdframework.R;
import vUtils.ViewUtils;

/**
 * @author: FYL
 * @time: 2018/9/25
 * @email:347933430@qq.com
 * @describe: rapid.com.rdframework.utilsdemo
 */
public class UtilsDemoActivity extends BaseActivity {
    @ViewInject(value = R.id.utils_recycler)
    private RecyclerView utils_recycler;
    @Override
    public int bindLayout() {
        //此方法会在绑定布局之前执行
        setSteepStatusBar(true);//是否沉浸状态栏，默认true
        setAllowFullScreen(true);//是否允许全屏，默认true
        setScreenRoate(false);//是否禁止旋转屏幕 false:禁止，true:不禁止
        //是否设置状态栏颜色,默认true,默认颜色：default_color：#b5b5b5
        setSetActionBarColor(true, rapid.com.base.R.color.default_color);
        return R.layout.utils_demo_activity;
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
        utils_recycler.setLayoutManager(ViewUtils.getLayoutManager(mContext));
        List<String> lists = UtilsConstant.getUtilsName();
        bindAdapter(lists);
    }

    private void bindAdapter(final List<String> lists) {
        BaseRecyclerAdapter<String> adapter = new BaseRecyclerAdapter<String>(mContext,lists,R.layout.utils_recycler_item_layout) {
            @Override
            public void convert(BaseRecyclerHolder holder, final String item, int position) {
                holder.setText(R.id.utils_name,item+"");
            }
        };
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //跳转工具详情
//                showToast("跳转至"+lists.get(position)+"工具类详情！");
                UtilsConstant.startUtilActivity(UtilsDemoActivity.this,lists.get(position));
            }
        });
        utils_recycler.setAdapter(adapter);
    }

    @Override
    public void widgetClick(View v) {

    }
}
