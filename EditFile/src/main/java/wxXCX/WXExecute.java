package wxXCX;

import com.zhkj.execute.EditImageExecute;
import com.zhkj.log.Log;
import com.zhkj.tools.FileUtils;

import java.io.File;

/**
 * @author: FYL
 * @time: 2019/1/10
 * @email:347933430@qq.com
 * @describe: wxXCX
 */
public class WXExecute {

    private static String pagesDirectory;//图片目录
    public static void exe( String pages){
        pagesDirectory = pages;
        try {
            /**修改图片名称*/
            if(FileUtils.isExists(pagesDirectory)){
                Log.appendInfo("开始修改图片>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                EditWxExecute.executePages(new File(pagesDirectory));
            }else{
                Log.appendErr("图片文件目录不存！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
