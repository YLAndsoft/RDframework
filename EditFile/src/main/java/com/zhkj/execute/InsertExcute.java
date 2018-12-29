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
 * @time: 2018/12/5
 * @email:347933430@qq.com
 * @describe: 插入无用代码执行类
 */
public class InsertExcute {

    /**
     * 执行类
     * @param file
     */
    public static void insertCode(File file,File newFile){
        BufferedReader br = null;
        FileOutputStream outputStream = null;
        OutputStreamWriter streamWriter = null;
        BufferedWriter w = null;
        //创建临时文件
//        File newWidgetPath = FileUtils.createNewFile(file.getAbsolutePath());
        try{
            //2.创建流
            br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            outputStream = new FileOutputStream(newFile);
            streamWriter = new OutputStreamWriter(outputStream,"UTF-8");
            w = new BufferedWriter(streamWriter);
            String line = null;
            String tmp = null;//临时行
            while((line = br.readLine())!=null) {
                tmp = line;
//                tmp = EditTools.insertCode(tmp);//插入无用代码
                tmp = EditTools.filter(tmp);//过滤注释
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
}
