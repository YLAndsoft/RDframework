package ylfile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: FYL
 * @time: 2019/1/11
 * @email:347933430@qq.com
 * @describe: 文件的操作
 */
public class FileUtils {

    /**
     * 得到文件后缀
     * @param file 文件
     * @param type 返回类型
     * @return 1：去掉后缀之后的文件名称  2：返回后缀
     * 默认返回带后缀的文件名称
     */
    public static String getFileSuffix(final File file,final int type){
        try{
            String metaName = file.getName();//得到文件名称
            String meteRegex = "(.*)(\\.\\w+)$";
            Pattern pattern = Pattern.compile(meteRegex);
            Matcher matcher = pattern.matcher(metaName);
            if(matcher.find()){
                return type==1?matcher.group(1):matcher.group(2);
            }
            return metaName;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    /**
     * 根据文件路径获取文件
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFilePath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }
    /**
     * 重命名文件
     * @param file    文件
     * @param newName 新名称
     * @return {@code true}: 重命名成功<br>{@code false}: 重命名失败
     */
    public static boolean rename(final File file, final String newName) {
        // 文件为空返回 false
        if (file == null) return false;
        // 文件不存在返回 false
        if (!file.exists()) return false;
        // 新的文件名为空返回 false
        if (isSpace(newName)) return false;
        // 如果文件名没有改变返回 true
        if (newName.equals(file.getName())) return true;
        File newFile = new File(file.getParent() + File.separator + newName);
        // 如果重命名的文件已存在返回 false
        return !newFile.exists()
                && file.renameTo(newFile);
    }
    /**
     * 简单获取文件编码格式
     *
     * @param filePath 文件路径
     * @return 文件编码
     */
    public static String getFileCharsetSimple(final String filePath) {
        return getFileCharsetSimple(getFilePath(filePath));
    }

    /**
     * 获取文件编码格式
     * @param file 文件
     * @return 文件编码
     */
    public static String getFileCharsetSimple(final File file) {
        int p = 0;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            p = (is.read() << 8) + is.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        switch (p) {
            case 0xefbb:
                return "UTF-8";
            case 0xfffe:
                return "Unicode";
            case 0xfeff:
                return "UTF-16BE";
            default:
                return "GBK";
        }
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
