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
     * @param fileName 场景文件目录
     */
    public static void start(String fileName) throws Exception{
        File file = new File(fileName);
        File files[] = file.listFiles();
        if(null==files||files.length<0){return;}
        for(int i=0;i<files.length;i++){
            if(files[i].isFile()){
                execute(files[i]);
                //删除修改之前的文件
                if(files[i].exists()){
                    Log.appendInfo("删除文件>>>>>"+files[i].getName());
                    files[i].delete();
                }
            }else{
                Log.appendInfo("需要修改场景文件目录并没有文件存在！！！");
            }
        }

    }
    /**
     * 执行方法
     * @param senceFile 场景文件
     */
    private static void execute(File senceFile){
        BufferedReader br = null;
        FileOutputStream outputStream = null;
        OutputStreamWriter streamWriter = null;
        BufferedWriter w = null;
        //创建临时文件
        File newWidgetPath = FileUtils.createNewFile(senceFile.getAbsolutePath());
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
