package rapid.com.tools;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;
import java.text.DecimalFormat;

/**SD卡工具类
 * Created by DN on 2017/9/6.
 */

public class SDCardUtils {
    public static String getSDAvailableSize(Context paramContext) {
        File path = Environment.getExternalStorageDirectory();
        StatFs localStatFs = new StatFs(path.getPath());
        //long availBlocksSize = localStatFs.getAvailableBlocksLong();
        long availBlocksSize = 0;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            availBlocksSize = localStatFs.getAvailableBlocksLong();
        }else {
            availBlocksSize =localStatFs.getBlockSize();
        }
        //long blockSize = localStatFs.getBlockSizeLong();//每个block的大小
        long blockSize = 0;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            blockSize = localStatFs.getBlockSizeLong();
        }else {
            blockSize =localStatFs.getBlockSize();
        }
        return Formatter.formatFileSize(paramContext, availBlocksSize * blockSize);
    }

    public static long getSDAvailableSizeLong() {
        File path = Environment.getExternalStorageDirectory();
        StatFs localStatFs = new StatFs(path.getPath());
        //long availBlocksSize = localStatFs.getAvailableBlocksLong();
        long availBlocksSize = 0;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            availBlocksSize = localStatFs.getAvailableBlocksLong();
        }else {
            availBlocksSize =localStatFs.getBlockSize();
        }
        //long blockSize = localStatFs.getBlockSizeLong();//每个block的大小
        long blockSize = 0;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            blockSize = localStatFs.getBlockSizeLong();
        }else {
            blockSize =localStatFs.getBlockSize();
        }
        return availBlocksSize * blockSize;
    }

    public static boolean isSDFreeSize(long paramLong) {
        long SDAvaiSize = getSDAvailableSizeLong();
        if(SDAvaiSize>=paramLong)
        {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取SD卡的状态
     */
    public static String getState() {
        return Environment.getExternalStorageState();
    }


    /**
     * SD卡是否可用
     *
     * @return 只有当SD卡已经安装并且准备好了才返回true
     */
    public static boolean isAvailable() {
        return getState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 获取SD卡的根目录
     *
     * @return null：不存在SD卡
     */
    public static File getRootDirectory() {
        return isAvailable() ? Environment.getExternalStorageDirectory() : null;
    }


    /**
     * 获取SD卡的根路径
     *
     * @return null：不存在SD卡
     */
    public static String getRootPath() {
        File rootDirectory = getRootDirectory();
        return rootDirectory != null ? rootDirectory.getPath() : null;
    }
    /**
     *获取sd卡路径
     * @return Stringpath
     */
    public static String getSDPath(){

        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if   (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();

    }

    /**
     * 文件大小long转单位
     * @param memory
     * @return
     */
    public static String format1(long memory) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (memory < 1024) {
            fileSizeString = df.format((double) memory) + "B";
        } else if (memory < 1048576) {
            fileSizeString = df.format((double) memory / 1024) + "KB";
        } else if (memory < 1073741824) {
            fileSizeString = df.format((double) memory / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) memory / 1073741824) + "G";
        }
        return fileSizeString;


    }





}
