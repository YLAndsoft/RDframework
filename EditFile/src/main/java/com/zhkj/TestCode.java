package com.zhkj;

import com.zhkj.tools.EditTools;

import java.io.BufferedWriter;
import java.io.File;
import java.util.regex.Matcher;

import ylfile.YLFiles;

/**
 * @author: FYL
 * @time: 2018/12/4
 * @email:347933430@qq.com
 * @describe: 单元测试类
 */
public class TestCode{

    public static void start(){
        String path = "G:/wGame/LayaWorkSpace_hx/zimu/zimu-625-lingjiang/zimu-625/src/ui/layaUI.max.all.ts";
        YLFiles.getInstance(new YLFiles.FileReadListener() {
            @Override
            public void fail(String ex) {
                System.out.println(ex);
            }
            @Override
            public String readLines(String line) {
                //		public btn_tips:Laya.Image;
                //		public tips_txt:laya.display.Text;
                String widgetRegex1 = "(\\s*public\\s*)(\\w+)(:laya\\.||:Laya\\.)(.*;)";
                Matcher matcher = EditTools.getMatcher(line,widgetRegex1);
                if(matcher.find()){
                    System.out.println("关键字："+matcher.group(1)+"----关键字："+matcher.group(2)+"----关键字："+matcher.group(3));
                }
                return line;
            }
        }).read(new File(path));
    }
    public static void main(String [] a){
        start();
    }


}
