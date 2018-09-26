package rapid.com.rdframework;

import android.app.Application;

import org.xutils.x;

import rapid.com.db.DBManager;
import rapid.com.tools.SpanUtils;

/**
 * @author: FYL
 * @time: 2018/9/17
 * @email:347933430@qq.com
 * @describe: rapid.com.rdframework
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志
        DBManager.initDB();//创建数据库
        SpanUtils.init(this);
    }
}
