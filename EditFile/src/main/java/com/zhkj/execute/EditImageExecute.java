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
 * @describe: 修改图片执行类
 */
public class EditImageExecute {

    /**
     * 修改图片
     * @param imagegFile 图片文件夹
     */
    public static void execute(File imagegFile){
        File files[] = imagegFile.listFiles();
        if(null==files||files.length<0){return;}
        for(int i=0;i<files.length;i++){
            if(files[i].isFile()){
                //修改图片名称,并把修改的记录保存到集合里面
                EditTools.editImageName(files[i]);
            }else{
                execute(files[i]);
            }
        }
    }
    /**
     * 修改图片
     * @param imagegFile 图片文件夹
     */
    public static void executeCocos(File imagegFile){
        File files[] = imagegFile.listFiles();
        if(null==files||files.length<0){return;}
        for(int i=0;i<files.length;i++){
            if(files[i].isFile()){
                //修改图片名称,并把修改的记录保存到集合里面
                EditTools.editImageName(files[i]);
            }else{
                executeCocos(files[i]);
            }
        }
        //修改mete文件
        executeMeta(imagegFile);
    }

    /**
     * 修改meta文件的执行方法
     * @param metaFile
     */
    private static void executeMeta(File metaFile){
        File files[] = metaFile.listFiles();
        if(null==files||files.length<0){return;}
        for(int i=0;i<files.length;i++){
            if(files[i].isFile()){
                //判断是否是.meta文件
                boolean isMeta = EditTools.isMetaFile(files[i]);
                if(isMeta){
                    //创建临时文件
                    File newWidgetPath = FileUtils.createNewFile(files[i].getAbsolutePath());
                    editMetaFile(files[i],newWidgetPath);//执行修改内容
                    //删除修改之前的文件，并把修改之后的文件改名
                    if(newWidgetPath.exists()){
                        String fileName = files[i].getName();
                        String newFileName = EditTools.editFileName(fileName);
                        File newFile = new File(files[i].getParent()+"/"+newFileName);
                        files[i].delete();
                        newWidgetPath.renameTo(newFile);
                    }
                }
            }else{
                executeMeta(files[i]);
            }
        }
    }

    /**
     * 修改meta文件
     * @param file
     * @param newWidgetPath
     */
    private static void editMetaFile(File file,File newWidgetPath){
        BufferedReader br = null;
        FileOutputStream outputStream = null;
        OutputStreamWriter streamWriter = null;
        BufferedWriter w = null;
        try{
            //2.创建流
            br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            outputStream = new FileOutputStream(newWidgetPath);
            streamWriter = new OutputStreamWriter(outputStream,"UTF-8");
            w = new BufferedWriter(streamWriter);
            String line = null;
            String tmp = null;//临时行
            while((line = br.readLine())!=null) {
                tmp = line;
                tmp = EditTools.editMeta(tmp);
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
     * @param a
     */
   /* public static void main(String [] a){
        String path = "G:/wGame/LayaWorkSpace_hx/shenshou/Test/comp";
        try {
//            execute(path);//测试通过！
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

}
