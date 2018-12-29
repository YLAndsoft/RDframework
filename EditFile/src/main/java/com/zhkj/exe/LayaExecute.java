package com.zhkj.exe;

import com.zhkj.execute.EditImageExecute;
import com.zhkj.execute.EditJSExecute;
import com.zhkj.execute.EditSceneExecute;
import com.zhkj.execute.EditWidgetExecute;
import com.zhkj.log.Log;
import com.zhkj.tools.FileUtils;

/**
 * @author: FYL
 * @time: 2018/12/4
 * @email:347933430@qq.com
 * @describe: 执行类
 */
public class LayaExecute {

    private static String widgetPath;//控件
    private static String imageDirectory;//图片目录
    private static String sceneDirectory;//场景文件目录
    private static String editJsDirectory;//要修改的JS文件目录

    public static void exe(String widget, String image, String scene, String editJs){
        widgetPath = widget;
        imageDirectory = image;
        sceneDirectory = scene;
        editJsDirectory = editJs;
        //创建控件修改之后的JS文件
//        newWidgetFile =  FileUtils.createNewFile(widgetPath);
        try {
            /**修改图片名称*/
            if(FileUtils.isExists(imageDirectory)){
                Log.appendInfo("开始修改图片>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                EditImageExecute.execute(imageDirectory);
            }else{
                Log.appendErr("图片文件目录不存！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            /**修改场景文件:主要是修改图片文件调用和控件调用*/
            if(FileUtils.isExists(sceneDirectory)){
                Log.appendInfo("开始场景文件>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                EditSceneExecute.start(sceneDirectory);
            }else{
                Log.appendErr("场景文件目录不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            /**修改混淆JS文件:主要是方法，字段，引用的图片和引用的控件*/
            if(FileUtils.isExists(editJsDirectory)){
                Log.appendInfo("开始混淆JS文件>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                EditJSExecute.start(editJsDirectory);
            }else{
                Log.appendErr("要修改的JS文件目录不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  /*  public static void main(String [] a){
        String widgetpath = "G:/wGame/LayaWorkSpace_hx/shenshou/Test/layaUI.max.all.js";
        String imageDirectory = "G:/wGame/LayaWorkSpace_hx/shenshou/Test/comp";
        String sceneDirectory = "G:/wGame/LayaWorkSpace_hx/shenshou/Test/pages";
        exe(widgetpath,imageDirectory,sceneDirectory,null);
    }*/


}
