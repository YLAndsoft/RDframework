package com.zhkj;


import com.zhkj.tools.EditTools;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;

/**
 * @author: FYL
 * @time: 2018/12/4
 * @email:347933430@qq.com
 * @describe: com.zhkj
 */
public class TestCode {

    public static void main(String [] a){
//        String meteRegex = "(\\w+)(\\.jpg|\\.png)(\\.meta)$";
        String regex = "(img_guan@2x)(\\.jpg|\\.png|\\.gif)";
        String str = "            this.yl.skin = 'img/img_guan@2x.png';";
        Matcher matcher = EditTools.getMatcher(str,regex);
        if(matcher.find()){
            System.out.println(matcher.group(1));
        }

//        Matcher matcher = EditTools.getMatcher(ss,regex);
//        if(matcher.find()){
//            System.out.println(s1);
//        }
        String aaa = "\tccL : function () { \n" +
                "var lAc = 0;\n" +
                "},\n" +
                "\n" +
                "\n" +
                "\t";
//        String bbb= aaa.replaceAll("\\r|\\n","");

//        Pattern pattern = Pattern.compile(re);
//        Matcher matcher = pattern.matcher(txt);
//        System.out.println(matcher.find());
           //if(matcher.find()){

          // }
    }

}
