package com.zhkj.execute;

import com.zhkj.tools.EditTools;
import com.zhkj.tools.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author: FYL
 * @time: 2018/12/4
 * @email:347933430@qq.com
 * @describe: 修改控件名称执行类
 */
public class EditWidgetExecute {

    public static void execute(String widgetPath) throws Exception{
        BufferedReader br = null;
//        FileOutputStream outputStream = null;
//        OutputStreamWriter streamWriter = null;
//        BufferedWriter w = null;
        try{
            //2.创建流
            br = new BufferedReader(new FileReader(new File(widgetPath)));//构造一个BufferedReader类来读取文件
//            outputStream = new FileOutputStream(newWidgetPath);
//            streamWriter = new OutputStreamWriter(outputStream,"UTF-8");
//            w = new BufferedWriter(streamWriter);
            String line = null;
//            String tmp = null;//临时行
            while((line = br.readLine())!=null) {
                EditTools.getWidgetValue(line);
//                FileUtils.saveFile(tmp,w);//保存至新文件中
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(br!=null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 单元测试
     * @param agrs
     */
   /* public static void main(String []agrs){
        String path = "G:/wGame/LayaWorkSpace_hx/shenshou/Test/layaUI.max.all.js";
        String newPath = "G:/wGame/LayaWorkSpace_hx/shenshou/Test/newlayaUI.max.all.js";
        try {
//            execute(path,new File(newPath));//测试通过
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
