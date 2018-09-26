package rapid.com.rdframework.dbdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.BaseAdapter;

import org.xutils.DbManager;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import adapter.BaseRecyclerAdapter;
import adapter.BaseRecyclerHolder;
import av.AV;
import base.BaseActivity;
import rapid.com.db.DBManager;
import rapid.com.rdframework.R;
import rapid.com.tools.TimeTools;
import vUtils.ViewUtils;

/**
 * @author: FYL
 * @time: 2018/9/14
 * @email:347933430@qq.com
 * @describe:
 */
public class SQLiteActivity extends BaseActivity {

    @ViewInject(value = R.id.sql_recycler)
    private RecyclerView sql_recycler;
    private BaseRecyclerAdapter<Student> adapter;
    private List<Student>  mList = new ArrayList<>();
    @Override
    public int bindLayout() {
        return R.layout.activity_a_layout;
    }

    @Override
    public void initView(View view) {
        x.view().inject(this);
        sql_recycler.setLayoutManager(ViewUtils.getLayoutManager(mContext));
        mList = DBManager.queryAll(Student.class);
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData(Context mContext) {
        if(mList==null||mList.size()<=0){
            AV.showSucess(mContext, "提示", "监测到没有数据,是否增加测试数据?", new AV.OnSucessListener() {
                @Override
                public void onSucess() {
//                    List<Student>  mList =  StudentData.getDBData();
                    mList = DBManager.insert(StudentData.getDBData(),Student.class);
                    Collections.sort(mList);
                    bindAdapter();
                }
            });
        }else{
            Collections.sort(mList);
            bindAdapter();
        }
    }
    private void bindAdapter() {
        adapter = new BaseRecyclerAdapter<Student>(mContext,mList,R.layout.db_recycler_item_layout) {
            @Override
            public void convert(BaseRecyclerHolder holder, Student item, int position) {
                holder.setText(R.id.user_name,item.getUserName()+"");
                holder.setText(R.id.add_time,item.getAddTime()+"");
                holder.setOnViewClick(R.id.addStudent, item, position, new BaseRecyclerHolder.OnViewClickListener() {
                    @Override
                    public void onViewClick(View view, Object object, int position) {
//                            showToast("点击了添加");
                        Student student = (Student) object;
                        String times = TimeTools.date2String(new Date());
                        Student addStudent = new Student("增加数据"+position,student.getUserSex(),student.getUserAge(),student.getUserAddress(),times);
                        adapter.insert(addStudent);
                        DBManager.save(addStudent,Student.class);
                    }
                });
            }
        };
        sql_recycler.setAdapter(adapter);
    }

    @Override
    public void widgetClick(View v) {

    }


}
