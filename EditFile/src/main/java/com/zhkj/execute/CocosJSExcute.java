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
 * @time: 2018/12/24
 * @email:347933430@qq.com
 * @describe: 修改CocosJS文件
 */
public class CocosJSExcute {

    /**
     * 启动方法
     * @param editJsDirectory
     */
    public static void start(String editJsDirectory){
        File file = new File(editJsDirectory);
        File[] files = file.listFiles();
        if(null==files||files.length<0){return;}
        checkFile(files);
        upFile(files);

    }

    /**
     * 检查所有JS文件里面的字段方法
     * @param files
     */
    private static void checkFile(File[] files) {
        //遍历所有JS文件,得到方法名和字段名
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //得到所有的字段，方法名
                Log.appendInfo("开始寻找"+files[i].getName()+"文件里面的字段方法------------------------------------------");
//                EditTools.getCocosFields(files[i]);
                EditTools.getCocosMethods(files[i]);
                Log.appendInfo("完成寻找------------------------------------------");
            } else {
                if (files[i].isDirectory()) {
                    File[] fils = files[i].listFiles();
                    if (null == fils || fils.length < 0) {
                        return;
                    }
                    checkFile(fils);
                }
            }
        }
    }

    /**
     * 开始去修改
     * @param files
     */
    private static void upFile(File[] files) {
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //创建临时文件
                File newWidgetPath = FileUtils.createNewFile(files[i].getAbsolutePath());
                execute(newWidgetPath,files[i]);//执行修改
                String fileName = files[i].getName();
                File newFile = new File(files[i].getParent()+"/"+fileName);
                files[i].delete();//删除修改之前的文件
                newWidgetPath.renameTo(newFile);//重命名修改之后的文件名称
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
//        创建临时文件
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
                //去修改方法名，字段名，控件名，图片名等引用的地方
                tmp = EditTools.editCocosJS(tmp);
                if(tmp.trim().length()>0){
                    FileUtils.saveFile(tmp,w);//保存至新文件中
                }
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
