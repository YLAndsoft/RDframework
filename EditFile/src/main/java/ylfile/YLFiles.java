package ylfile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * @author: FYL
 * @time: 2019/1/11
 * @email:347933430@qq.com
 * @describe: 文件的操作
 */
public class YLFiles {
    private static YLFiles files;
    private static FileReadListener listener;
    public static YLFiles getInstance(FileReadListener l){
        if (files == null) {
            files = new YLFiles();
        }
        listener = l;
        return files;
    }
    public interface FileReadListener{
        void fail(String ex);//失败
        String readLines(String line);//一行
    }
    /**
     * 读取文件内容
     * @param file
     */
    public static void read( File file){
        BufferedReader br = null;
        try{
            //2.创建流
            br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String readLine = null;//临时行
            while((readLine = br.readLine())!=null) {
                if(listener!=null){
                    listener.readLines(readLine);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            if(listener!=null){
                listener.fail(e.getMessage());
            }

        }finally {
            try {
                if(br!=null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件内容并写入到另外一个文件中(边读边写)
     * @param newFile 新文件
     * @param file 需要读取的文件
     */
    public static void readWriter(File newFile, File file){
        BufferedReader br = null;
        FileOutputStream outputStream = null;
        OutputStreamWriter streamWriter = null;
        BufferedWriter bw = null;
        try{
            //2.创建流
            br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            outputStream = new FileOutputStream(newFile);
            streamWriter = new OutputStreamWriter(outputStream,"UTF-8");
            bw = new BufferedWriter(streamWriter);
            String tmpLine = null;//临时行
            while((tmpLine = br.readLine())!=null) {
                if(listener!=null){
                    tmpLine = listener.readLines(tmpLine);
                    saveFile(tmpLine,bw);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            if(listener!=null){
                listener.fail(e.getMessage());
            }

        }finally {
            try {
                if(br!=null)  br.close();
                if(outputStream!=null) outputStream.close();
                if(bw!=null) bw.close();
                if(streamWriter!=null)streamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存修改后的字符串到一个新的文件中去
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

}
