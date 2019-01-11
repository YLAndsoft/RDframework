package wxXCX;

import com.zhkj.log.Log;
import com.zhkj.tools.EditTools;

/**
 * @author: FYL
 * @time: 2019/1/10
 * @email:347933430@qq.com
 * @describe: wxXCX
 */
public class TextCode {

    public static void main(String [] args){
        String tmp = "          url: '../AAAA/flgaem?level=0&type=1&gold=' + gold,";
        String key = "flgaem";
        String value = "AAAAA";
        int index = tmp.indexOf(key);
        if(index>0){
            String subFrist = tmp.substring(index-1,index);//得到key前面一个字符
            String subLast = tmp.substring(index+key.length(),index+key.length()+1);//得到key后面一个字符
            if("/".equals(subFrist)&&"/".equals(subLast)){ //前后都是/的话，说明是目录名
                String regex = "(\\/\\b"+key+"\\/\\b)";
                tmp =  tmp.replaceAll(regex,"/"+value+"/");
                System.out.println("##"+tmp+">>> ##替换的关键字>>"+key);
            }
            if("/".equals(subFrist)&&!"/".equals(subLast)){  //前面是/后面是不是，说明是js文件名
                String regex = "(\\/\\b"+key+"\\b)";
                tmp =  tmp.replaceAll(regex,"/"+value);
                System.out.println("##"+tmp+">>> ##替换的关键字>>"+key);
            }
        }
        System.out.println("------结束>>>>>>");
    }


}
