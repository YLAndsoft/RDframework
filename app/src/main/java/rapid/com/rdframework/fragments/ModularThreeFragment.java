package rapid.com.rdframework.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cv4j.core.datamodel.ByteProcessor;
import com.cv4j.core.datamodel.CV4JImage;
import com.cv4j.core.datamodel.ImageProcessor;
import com.cv4j.core.hist.CalcHistogram;
import com.cv4j.core.hist.CompareHist;
import com.cv4j.core.hist.EqualHist;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.BaseFragment;
import rapid.com.rdframework.R;


/**
 * Created by DN on 2017/9/6.
 */

public class ModularThreeFragment extends BaseFragment {

    @ViewInject(value = R.id.result)
    TextView result;
    @ViewInject(value = R.id.image_a)
    ImageView image_a;
    @ViewInject(value = R.id.image_b)
    ImageView image_b;
    @Override
    public int bindLayout() {
        return R.layout.modularthree_layout;
    }

    @Override
    protected void initView() {
        x.view().inject(this,mContextView);
    }

    @Override
    protected void initData() {
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.aaa);
        image_a.setImageBitmap(bitmap);

        CV4JImage cv4jImage = new CV4JImage(bitmap);
        ImageProcessor imageProcessor = cv4jImage.convert2Gray().getProcessor();

        int[][] source = null;
        int[][] target = null;

        CalcHistogram calcHistogram = new CalcHistogram();
        int bins = 180;
        source = new int[imageProcessor.getChannels()][bins];
        calcHistogram.calcHSVHist(imageProcessor,bins,source,true);

        if (imageProcessor instanceof ByteProcessor) {
            EqualHist equalHist = new EqualHist();
            equalHist.equalize((ByteProcessor) imageProcessor);
            image_b.setImageBitmap(cv4jImage.getProcessor().getImage().toBitmap());

            target = new int[imageProcessor.getChannels()][bins];
            calcHistogram.calcHSVHist(imageProcessor,bins,target,true);
        }

        CompareHist compareHist = new CompareHist();
        StringBuilder sb = new StringBuilder();
        sb.append("巴氏距离:").append(compareHist.bhattacharyya(source[0],target[0])).append("\r\n")
                .append("协方差:").append(compareHist.covariance(source[0],target[0])).append("\r\n")
                .append("相关性因子:").append(compareHist.ncc(source[0],target[0]));

        result.setText(sb.toString());
    }

    @Override
    public void widgetClick(View v) {

    }
}
