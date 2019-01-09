package com.zhkj.execute;

import com.zhkj.log.Log;
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
 * @describe: 修改场景文件执行类
 */
public class EditSceneExecute {

    /**
     * 启动方法
     * @param  file 场景文件目录
     */
    public static void start(File file){
        File files[] = file.listFiles();
        if(null==files||files.length<0){return;}
        for(int i=0;i<files.length;i++){
            if(files[i].isFile()){
                //创建临时文件
                File newWidgetPath = FileUtils.createNewFile(files[i].getAbsolutePath());
                execute(files[i],newWidgetPath);
                //删除修改之前的文件
                if(files[i].exists()){
                    String filesss = files[i].getName();
                    File newFile = new File(files[i].getParent()+"/"+filesss);
                    files[i].delete();//删除修改之前的文件
                    newWidgetPath.renameTo(newFile);//重命名修改之后的文件名称
                    Log.appendInfo("删除文件>>>>>"+files[i].getName());
//                    EditTools.clearClazzValue();//清空类
                }
            }else{
                start(files[i]);
            }
        }

    }


    /**
     * 执行方法
     * @param senceFile 场景文件
     */
    private static void execute(File senceFile,File newWidgetPath){
        BufferedReader br = null;
        FileOutputStream outputStream = null;
        OutputStreamWriter streamWriter = null;
        BufferedWriter w = null;
        try{
            //2.创建流
            br = new BufferedReader(new FileReader(senceFile));//构造一个BufferedReader类来读取文件
            outputStream = new FileOutputStream(newWidgetPath);
            streamWriter = new OutputStreamWriter(outputStream,"UTF-8");
            w = new BufferedWriter(streamWriter);
            String line = null;
            String tmp = null;//临时行
            while((line = br.readLine())!=null) {
                tmp = line;
                tmp = EditTools.editSecnce(tmp);
                FileUtils.saveFile(tmp,w);//保存至新文件中
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(br!=null) {
                    br.close();
                }
                if(outputStream!=null){
                    outputStream.close();
                }
                if(w!=null){
                    w.close();
                }
                if(streamWriter!=null){
                    streamWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    /**
     * 单元测试
     * @param ars
     */
 /*    public static void main(String ars[]){
       String path = "G:/wGame/LayaWorkSpace_hx/shenshou/Test/pages";

//        start(path);
    }*/


}
