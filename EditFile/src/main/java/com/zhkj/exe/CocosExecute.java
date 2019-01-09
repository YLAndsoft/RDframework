package com.zhkj.exe;

import com.zhkj.execute.CocosJSExcute;
import com.zhkj.execute.CocosSceneExecute;
import com.zhkj.execute.EditImageExecute;
import com.zhkj.execute.EditJSExecute;
import com.zhkj.log.Log;
import com.zhkj.tools.FileUtils;

import java.io.File;

/**
 * @author: FYL
 * @time: 2018/12/24
 * @email:347933430@qq.com
 * @describe: cocos混淆执行类
 */
public class CocosExecute {
    private static String imageDirectory;//图片目录
    private static String editJsDirectory;//要修改的JS文件目录
    private static String sceneDirectory;//要修改场景文件目录
    public static void exe( String image, String editJs,String scene){
        imageDirectory = image;
        editJsDirectory = editJs;
        sceneDirectory = scene;
        try {
            /**修改图片名称*/
            if(FileUtils.isExists(imageDirectory)){
                Log.appendInfo("开始修改图片>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                EditImageExecute.executeCocos(new File(imageDirectory));
            }else{
                Log.appendErr("图片文件目录不存！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            /**修改混淆JS文件:主要是方法，字段，引用的图片*/
            if(FileUtils.isExists(editJsDirectory)){
                Log.appendInfo("开始混淆JS文件>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                CocosJSExcute.start(editJsDirectory);
            }else{
                Log.appendErr("要修改的JS文件目录不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(FileUtils.isExists(sceneDirectory)){
                Log.appendInfo("开始混淆场景文件>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                CocosSceneExecute.startScene(sceneDirectory);
            }else{
                Log.appendErr("要修改的场景文件目录不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
