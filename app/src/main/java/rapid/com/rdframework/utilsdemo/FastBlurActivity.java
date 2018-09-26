package rapid.com.rdframework.utilsdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.BaseActivity;
import rapid.com.rdframework.R;
import rapid.com.tools.FastBlur;

/**
 * @author: FYL
 * @time: 2018/9/26
 * @email:347933430@qq.com
 * @describe: rapid.com.rdframework.utilsdemo
 */
public class FastBlurActivity extends BaseActivity {
    @ViewInject(value = R.id.fastblur_txt)
    private TextView fastblur_txt;
    @ViewInject(value = R.id.fastblur)
    private ImageView fastblur;
    @Override
    public int bindLayout() {
        return R.layout.fastblur_activity;
    }
    @Override
    public void initView(View view) {
        x.view().inject(this);
        fastblur_txt.setText(UtilsConstant.fastblur);
    }
    @Override
    public void initListener() {
    }

    /**使用说明*/
    @Override
    public void initData(Context mContext) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.girl);
        //bitmap，缩放比率，虚化程度-数值越大效果越明显
        Bitmap bitmap2 = FastBlur.doBlur(bitmap, 1, 50);
        // 位图,缩放后的宽度,缩放后的高度,虚化程度
        // Bitmap bitmap3 = FastBlur.doBlur(bitmap, 200,135,15);
        fastblur.setImageBitmap(bitmap2);
    }


    @Override
    public void widgetClick(View v) {

    }
}
