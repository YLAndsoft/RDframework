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
 * @time: 2018/12/25
 * @email:347933430@qq.com
 * @describe: 场景文件的执行类
 */
public class CocosSceneExecute {

    public static void startScene(String sceneDirectory) {
        File file = new File(sceneDirectory);
        File[] files = file.listFiles();
        if(null==files||files.length<0){return;}
        upFile(files);
    }
    /**
     * 开始去修改
     * @param files
     */
    private static void upFile(File[] files) {
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                if(EditTools.isFireFile(files[i])){
                    //创建临时文件
                    File newWidgetPath = FileUtils.createNewFile(files[i].getAbsolutePath());
                    execute(newWidgetPath,files[i]);//执行修改
                    String fileName = files[i].getName();
                    File newFile = new File(files[i].getParent()+"/"+fileName);
                    files[i].delete();//删除修改之前的文件
                    newWidgetPath.renameTo(newFile);//重命名修改之后的文件名称
                }
            } else {
                if (files[i].isDirectory()) {
                    File[] fils = files[i].listFiles();
                    if (null == fils || fils.length < 0) {
                        return;
                    }
                    upFile(fils);
                }
            }
        }
    }

    /**
     * 执行方法
     * @param file
     */
    private static void execute(File newFile,File file){
        BufferedReader br = null;
        FileOutputStream outputStream = null;
        OutputStreamWriter streamWriter = null;
        BufferedWriter w = null;
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
                //修改场景文件对控件，方法的引用
                tmp = EditTools.editScene(tmp);
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
