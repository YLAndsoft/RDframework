package rapid.com.rdframework.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.BaseRecyclerAdapter;
import adapter.BaseRecyclerHolder;
import base.BaseFragment;
import rapid.com.rdframework.R;
import vUtils.ViewUtils;


/**
 * Created by DN on 2017/9/6.
 * 模块一
 */

public class ModularOneFragment extends BaseFragment {

    @ViewInject(value = R.id.refreshLayout)
    private SmartRefreshLayout refreshLayout;
    @ViewInject(value = R.id.banner)
    private Banner banner;
    @ViewInject(value = R.id.recycler)
    private RecyclerView recycler;

    private BaseRecyclerAdapter<String> adapter;
    private List<String> mLists = new ArrayList<>();

    @Override
    public int bindLayout() {
        return R.layout.modulartwo_layout;
    }

    @Override
    protected void initView() {
        x.view().inject(this,mContextView);
        //设置管理器
        recycler.setLayoutManager(ViewUtils.getLayoutManager(mContext));
        //设置初始化控件完成后自动获取数据并刷新
        refreshLayout.autoRefresh();
        //打开上拉加载
        refreshLayout.setEnableLoadMore(true);
    }

    @Override
    protected void initData() {
        initBanner(getImages());
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            /**
             * 加载更多
             * @param refreshLayout
             */
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                List<String> lsits = getLData();
                adapter.addAll(lsits);
                refreshLayout.finishLoadMore();
            }
            /**
             * 刷新
             * @param refreshLayout
             */
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mLists.clear();
                getRData();
                bindData();
                refreshLayout.finishRefresh();
            }
        });

    }

    /**
     * 初始化Banner操作
     */
    private void initBanner(List<String> images) {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.CubeOut);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    /**
     * 绑定适配器
     */
    private void bindData() {
        adapter = new BaseRecyclerAdapter<String>(mContext,mLists,R.layout.recycler_view_item_layout) {
            @Override
            public void convert(BaseRecyclerHolder holder, String item, int position) {
                holder.setText(R.id.item_data,item+"");
            }
        };
        recycler.setAdapter(adapter);
    }
    private void getRData() {
        for(int i=0;i<20;i++){
            mLists.add("这是刷新数据"+i);
        }
    }
    private List<String> getLData() {
        List<String> lists = new ArrayList<>();
        for(int i=0;i<10;i++){
            lists.add("这是加载更多数据"+i);
        }
        return lists;
    }

    private List<String> getImages(){
        List<String> images = new ArrayList<>();
        images.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=500808421,1575925585&fm=200&gp=0.jpg");
        images.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=426595056,3152484396&fm=27&gp=0.jpg");
        images.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=973931041,1439083005&fm=27&gp=0.jpg");
        images.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4004969984,952309171&fm=200&gp=0.jpg");
        images.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3817831153,1208367360&fm=27&gp=0.jpg");
        return images;
    }
    @Override
    public void widgetClick(View v) {

    }

    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            //Glide 加载图片简单用法
            Glide.with(mContext).load(path).into(imageView);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        /*@Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            return null;
        }*/
    }

}
