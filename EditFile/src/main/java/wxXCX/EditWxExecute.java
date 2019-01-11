package wxXCX;

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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author: FYL
 * @time: 2019/1/10
 * @email:347933430@qq.com
 * @describe: wxXCX
 */
public class EditWxExecute {
    public static Map<String, String> mapFiles = new HashMap<>();
    public static Map<String, String> mapDirectory = new HashMap<>();
    private static File parentFile;
    public static void executePages(File pagesFile) {
        File[] files = pagesFile.listFiles();
        parentFile = pagesFile.getParentFile();//得到父目录，便于寻找app.json文件
        if (null == files || files.length < 0) {
            return;
        }
        //得到pages目录下的所有文件和文件夹名称，并修改
        checkOrEditFile(files);
        for (Map.Entry<String, String> entry : mapDirectory.entrySet()) {
            Log.appendInfo("目录名称： "+entry.getKey()+">>  " + entry.getValue());
        }
        for (Map.Entry<String, String> entry : mapFiles.entrySet()) {
            Log.appendInfo("文件名称： "+entry.getKey()+">>  " + entry.getValue());
        }
        //检查JS文件里面的方法
        files = pagesFile.listFiles();
        checkFileMethod(files);
        //开始修改
        start(files);
        //修改app.json文件
        updateAPPJson(parentFile);
        //加密config配置文件
        EditTools.isInsertUncompile(false);
        compileConfigFile(parentFile);
    }

    /**
     * 加密config文件
     */
    private static void compileConfigFile(File parentFile) {
        File [] files = parentFile.listFiles();
        if(null==files||files.length<=0){return;}
        for(int i= 0;i<files.length;i++){
            if (files[i].isFile()) {
                if(EditTools.isConfFile(files[i])){ //判断是否是conf.js文件
                    //创建临时文件
                    File newWidgetPath = FileUtils.createNewFile(files[i].getAbsolutePath());
                    executeConfJs(newWidgetPath,files[i]);//执行修改
                    String fileName = files[i].getName();
                    File newFile = new File(files[i].getParent()+"/"+fileName);
                    files[i].delete();//删除修改之前的文件
                    newWidgetPath.renameTo(newFile);//重命名修改之后的文件名称
                }
            }
        }
    }

    /**
     * 修改app.json文件
     * @param parentFile
     */
    private static void updateAPPJson(File parentFile) {
        File [] files = parentFile.listFiles();
        if(null==files||files.length<=0){return;}
        for(int i= 0;i<files.length;i++){
            if (files[i].isFile()) {
                if(EditTools.isAppJsonFile(files[i])){ //判断是否是app.json文件
                    //创建临时文件
                    File newWidgetPath = FileUtils.createNewFile(files[i].getAbsolutePath());
                    executeAppJson(newWidgetPath,files[i]);//执行修改
                    String fileName = files[i].getName();
                    File newFile = new File(files[i].getParent()+"/"+fileName);
                    files[i].delete();//删除修改之前的文件
                    newWidgetPath.renameTo(newFile);//重命名修改之后的文件名称
                }
            }
        }
    }

    public static void start(File[] files){
        if(null==files||files.length<=0){return;}
        for(int i = 0; i < files.length; i++){
            if (files[i].isDirectory()) {
                updateFile(files);
            }
        }

    }

    /**
     * 开始去修改
     * @param files
     */
    private static void updateFile(File[] files) {
        if(null==files||files.length<=0){return;}
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                if(EditTools.isJSFile(files[i])||EditTools.isWXmlFile(files[i])){
                    //创建临时文件
                    File newWidgetPath = FileUtils.createNewFile(files[i].getAbsolutePath());
                    executeJS(newWidgetPath,files[i]);//执行修改
                    String fileName = files[i].getName();
                    File newFile = new File(files[i].getParent()+"/"+fileName);
                    files[i].delete();//删除修改之前的文件
                    newWidgetPath.renameTo(newFile);//重命名修改之后的文件名称
                }
            } else {
                if (files[i].isDirectory()) {
                    File[] fils = files[i].listFiles();
                    if (null == fils || fils.length < 0) {return;}
                    updateFile(fils);
                }
            }
        }
    }
    /**
     * 执行方法
     * @param file
     */
    private static void executeJS(File newFile,File file){
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
                tmp = EditTools.editWxPagesJS(tmp);
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
     * 执行方法,修改app.json文件
     * @param file
     */
    private static void executeAppJson(File newFile,File file){
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
                tmp = EditTools.editWxAppJson(tmp);
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
     * 执行方法,加密conf.js文件
     * @param file
     */
    private static void executeConfJs(File newFile,File file){
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
                tmp = EditTools.editConfJs(tmp);
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
     * 检查JS文件里面的方法
     * @param files
     */
    private static void checkOrEditFile(File[] files) {
        if (null == files || files.length < 0) {return;}
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) { //文件
                editFiles(files[i]);
            } else {
                //文件夹
                String fileName = files[i].getName();//得到原文件夹名称
                String editFileName = EditTools.getRandomString(EditTools.getRandomInt(3, 8));//得到修改之后的文件夹名
                mapDirectory.put(fileName, editFileName);//添加到集合里面
                //重命名文件夹名称
                File newFile = new File(files[i].getParent() + "/" + editFileName);
                boolean isEdit = files[i].renameTo(newFile);//重命名修改之后的文件夹名称
                editFile(newFile.listFiles());
            }
        }
    }
    private static void editFile(File[] files) {
        if (null == files || files.length < 0) {return;}
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) { //文件
                editFiles(files[i]);
            }
        }
    }

    /**
     * 重命名文件
     * @param files
     */
    public static void editFiles(File files) {
        if (files.isFile()) { //文件
            String fileName = EditTools.checkFile(files);//得到原文件名称
            if(!mapFiles.containsKey(fileName)){ //如果文件名存在，则不生成随机文件名称
                String editFileName = EditTools.getRandomString(EditTools.getRandomInt(3, 8));//得到修改之后的文件名
                mapFiles.put(fileName, editFileName);//添加到集合里面,保存后缀
            }
            String value = EditTools.getMapValue(fileName,mapFiles);
            //重命名文件名称
            File newFile = getNewFileName(files, value);
            boolean isEdit = files.renameTo(newFile);//重命名修改之后的文件名称
        } else {
            //再有文件目录，不管了
        }
    }

    /**
     * 根据file得到一个新的文件
     * @param fi
     */
    private static File getNewFileName(File fi, String editFileName) {
        String metaName = fi.getName();
        String meteRegex = "(.*)(\\.\\w+)$";
        Matcher matcher = EditTools.getMatcher(metaName, meteRegex);
        if (matcher.find()) {
            File file = new File(fi.getParent() + "/"+editFileName + matcher.group(2));
            return file;
        }
        return null;
    }

    /**
     * 检查所有JS文件里面的方法
     * @param files
     */
    private static void checkFileMethod(File[] files) {
        //遍历所有JS文件,得到方法名和字段名
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //得到所有的字段，方法名
                Log.appendInfo("开始寻找" + files[i].getName() + "文件里面的字段方法------------------------------------------");
                if (EditTools.isJSFile(files[i])) { //只有JS文件才去读取方法
                    EditTools.getCocosMethods(files[i]);
                    Log.appendInfo("完成寻找------------------------------------------");
                }
            } else {
                if (files[i].isDirectory()) {
                    File[] fils = files[i].listFiles();
                    if (null == fils || fils.length < 0) {
                        return;
                    }
                    checkFileMethod(fils);
                }
            }
        }
    }


   /* public static void main(String[] args) {
        String path = "G:/paopaoBZ/ssss/ads";
        executePages(new File(path));
    }*/


}
