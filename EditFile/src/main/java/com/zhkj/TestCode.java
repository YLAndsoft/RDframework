package com.zhkj;

import java.io.BufferedWriter;
import java.io.File;

import ylfile.YLFiles;

/**
 * @author: FYL
 * @time: 2018/12/4
 * @email:347933430@qq.com
 * @describe: 单元测试类
 */
public class TestCode{

    public static void start(){
        String path = "G:/游戏混淆/备份/zhkj/entity/ModuleAdress.java";
        String path1 = "G:/游戏混淆/备份/zhkj/entity/AAAA.java";
        YLFiles.getInstance(new YLFiles.FileReadListener() {
            @Override
            public void fail(String ex) {
                System.out.println(ex);
            }

            @Override
            public String readLines(String line) {
                System.out.println(line);
                return line;
            }
        }).readWriter(new File(path1),new File(path));
    }
    public static void main(String [] a){
        start();
    }


}
