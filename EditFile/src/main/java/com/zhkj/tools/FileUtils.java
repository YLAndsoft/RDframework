package com.zhkj.tools;

import com.zhkj.log.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * @author: FYL
 * @time: 2018/11/22
 * @email:347933430@qq.com
 * @describe: 文件管理类
 */
public class FileUtils {

    private static File tmpFile;
    /**
     * 判断文件是否存在
     * @param file
     * @return
     */
    public static boolean isExists(File file){
//        File file = new File(fi);
        if(file.exists())return true;
        return false;
    }
    public static boolean isExists(String file){
        if(null==file||"".equals(file)){
            return false;
        }
        if(new File(file).exists())return true;
        return false;
    }


    /**
     * 保存修改后的字符串到文件中去
     * @param str
     */
    public static void saveFile(String str, BufferedWriter writer) {
        if(str==null){return;}
        try {
            writer.write(str+System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *  创建要修改的文件
     * @param fi 修改之前的文件路径
     */
    public static File createNewFile(String fi){
        try {
            File file = new File(fi);
            if(file.exists()){
                String newFilePath = file.getParent();
                String newFileName = "new"+file.getName();
                File newFile = new File(newFilePath+"/"+newFileName);
                newFile.createNewFile();
                Log.appendInfo("创建文件："+newFileName);
                return newFile;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.appendInfo("创建文件异常："+e.getMessage());
        }
        return null;
    }

    /**
     * 创建日志文件
     * @param fi 修改JS文件的路径
     * @return 创建成功的文件路径
     */
    public static File createLogFile(String fi) {
        if(null==fi||"".equals(fi)){return null;}
        File file = new File(fi);
        if(file.exists()){
//            createTmpFile(fi);//创建临时文件夹，用来备份
            String logfile = file.getParentFile().getParent()+"/log.txt";
            try {
                File logFile = new File(logfile);
                logFile.createNewFile();
                return logFile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 创建临时文件夹
     * @param fi
     */
    private static void createTmpFile(String fi){
        if(null==fi||"".equals(fi)){return;}
        File file = new File(fi);
        if(file.exists()){
            String logfile = file.getParentFile().getParent()+"/tmp";
            tmpFile = new File(logfile);
            tmpFile.mkdirs();
        }
    }



}
