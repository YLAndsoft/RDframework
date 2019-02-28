package com.zhkj.exe;


import com.zhkj.log.Log;
import com.zhkj.project.ReadFileAddModule;
import com.zhkj.tools.FileUtils;
import com.zhkj.tools.ValueUtils;
import com.zhkj.ui.UITools;

import java.io.File;

import wxXCX.WXExecute;

/**
 * @author: FYL
 * @time: 2018/12/4
 * @email:347933430@qq.com
 * @describe: 混淆文件启动类
 */
public class EditStart {

    public static void main(String[] args){
        //初始化 UI界面 并显示
        UITools.run(600, 800);
    }
    /**
     * 点击启动的回调
     */
    public static void goOn(){
        synchronized (UITools.class) {
            start();
        }
    }
    /**
     * 启动
     */
    private static void start() {
        try{
            //控件的JS文件,用于修改控件名称
            String widgetPath = ReadFileAddModule.getValue(ValueUtils.WIDGET_PATH);
            //图片文件目录,用于修改图片文件名称
            String imageDirectory = ReadFileAddModule.getValue(ValueUtils.IMAGE_DIRECTORY);
            //修改JS文件目录
            String editJsDirectory = ReadFileAddModule.getValue(ValueUtils.EDITJS_DIRECTORY);
            //场景文件目录
            String sceneDirectory = ReadFileAddModule.getValue(ValueUtils.SCENE_DIRECTORY);
            //小程序混淆
            String pagesDirectory = ReadFileAddModule.getValue(ValueUtils.PAGES_DIRECTORY);
            //是否打印日志
            String isOpen = ReadFileAddModule.getValue(ValueUtils.IS_OPEN);
            //是否是Laya混淆
            String isLaya = ReadFileAddModule.getValue(ValueUtils.IS_LAYA);
            if(isOpen.equals("true")){
                //创建日志文件
                File logFile = FileUtils.createLogFile(imageDirectory==null?pagesDirectory==null?editJsDirectory:pagesDirectory:imageDirectory);
                Log.init(logFile,true);
            }else{
                Log.init(false);
            }
            if(null!=pagesDirectory){
                WXExecute.exe(pagesDirectory);
                return;
            }
            //修改控件名称，修改图片名称
            if(isLaya.equals("true")){
                //Laya
                LayaExecute.exe(widgetPath,imageDirectory,sceneDirectory,editJsDirectory);
            }else{
                //Cocos
                CocosExecute.exe(imageDirectory,editJsDirectory,sceneDirectory);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.appendErr(e.getMessage()+"");
            Log.appendErr("混淆异常！");
        }
    }


}
