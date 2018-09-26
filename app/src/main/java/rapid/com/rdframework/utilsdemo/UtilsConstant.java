package rapid.com.rdframework.utilsdemo;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: FYL
 * @time: 2018/9/25
 * @email:347933430@qq.com
 * @describe: rapid.com.rdframework.utilsdemo
 */
public class UtilsConstant {
    private static String [] utils = {"ComparableTools",
            "EventBusTools","FastBlur","FileIOUtils","FileUtils","GZipUtils",
            "ImageUtils","NetworkUtils","OtherUtils","PhoneUtil","RandomUtils","SDCardUtils","SpanUtils","StringUtils","TimeTools"};
    /**
     * 获取工具类名称
     */
    public static List<String> getUtilsName(){
        List<String> lists = new ArrayList<>();
        for(int i=0;i<utils.length;i++){
            lists.add(utils[i]);
        }
        return lists;
    }

    public static void startUtilActivity(Activity activity,String utilsName){
        switch (utilsName){
            case "ComparableTools":
                activity.startActivity(new Intent(activity,ComparableActivity.class));
                break;
            case "EventBusTools":
                activity.startActivity(new Intent(activity,EventBusActivity.class));
                break;
            case "FastBlur":
                activity.startActivity(new Intent(activity,FastBlurActivity.class));
                break;
            case "FileIOUtils":
                break;
            case "FileUtils":
                break;
            case "GZipUtils":
                break;
            case "ImageUtils":
                break;
            case "NetworkUtils":
                break;
            case "OtherUtils":
                break;
            case "PhoneUtil":
                break;
            case "RandomUtils":
                break;
            case "SDCardUtils":
                break;
            case "SpanUtils":
                break;
            case "StringUtils":
                break;
            case "TimeTools":
                break;

        }
    }

    /**排序说明*/
    public static String compaprablel = "\n" +
            "    /**使用例子*/\n" +
            "    public class Age extends ComparableTools{\n" +
            "        int age;\n" +
            "        String time;\n" +
            "        @Override\n" +
            "        public int compare(Object o) {\n" +
            "            Age o1 = (Age)o;\n" +
            "            //return this.age-o1.age;//比较int类型\n" +
            "            //时间比较\n" +
            "            Date data1 = TimeTools.string2Date(o1.time);\n" +
            "            Date data2 = TimeTools.string2Date(this.time);\n" +
            "            return data1.compareTo(data2);//比较时间类型\n" +
            "        }\n" +
            "    }\n" +
            "    public void main(String[] args){\n" +
            "        List<Age> list = new ArrayList<>();\n" +
            "        //list.addAll(...);\n" +
            "        //需要排序的地方调用这行代码即可\n" +
            "        Collections.sort(list);\n" +
            "    }\n";
    /**EventBus说明*/
    public static String eventbus = "\n" +
            "    /**使用：(如需使用更复杂的广播,请自行参考官网文档使用)*/\n" +
            "    @Override\n" +
            "    public void initListener() {\n" +
            "        //在onCreat()进行注册\n" +
            "        //EventBusTools.register(this);//注册广播\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public void initData(Context mContext) {\n" +
            "       //发送普通广播\n" +
            "       // EventBusTools.sendEvent(new EventBusEvent(0));" +"\n"+
            "       //发送粘性广播\n" +
            "      //EventBusTools.sendStickyEvent(new EventBusEvent(0));\n" +
            "    }\n" +
            "\n" +
            "    /**接收普通广播*/\n" +
            "    @Subscribe(threadMode= ThreadMode.MAIN)\n" +
            "    public void onEventMessage(EventBusEvent event) {\n" +
            "        //做自己的逻辑处理\n" +
            "    }\n" +
            "\n" +
            "    /**接收粘性广播*/\n" +
            "    @Subscribe(threadMode= ThreadMode.MAIN,sticky=true)\n" +
            "    public void onStickyEventMessage(EventBusEvent event) {\n" +
            "        //做自己的逻辑处理\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    protected void onDestroy() {\n" +
            "        super.onDestroy();\n" +
            "        //注销广播,建议在onDestroy()方法调用\n" +
            "        //EventBusTools.unregister(this);\n" +
            "    }\n" +
            "    /**封装的实体类*/\n" +
            "    public class EventBusEvent<T> {\n" +
            "        //区别码,当有多个广播的时候区分\n" +
            "        private int code;\n" +
            "        //携带的数据\n" +
            "        private T data;\n" +
            "        //当使用列表的时候用到的index下标,没用可以不用管\n" +
            "        private int position;\n" +
            "    }\n";

    public static String fastblur = "\n" +
            "    /**使用说明*/\n" +
            "    @Override\n" +
            "    public void initData(Context mContext) {\n" +
            "        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.girl);\n" +
            "        //bitmap，缩放比率，虚化程度-数值越大效果越明显\n" +
            "        Bitmap bitmap2 = FastBlur.doBlur(bitmap, 1, 15);\n" +
            "        // 位图,缩放后的宽度,缩放后的高度,虚化程度\n" +
            "        Bitmap bitmap3 = FastBlur.doBlur(bitmap, 200,135,15);\n" +
            "        fastblur.setImageBitmap(bitmap2);\n" +
            "    }\n" +
            "\n";

}
