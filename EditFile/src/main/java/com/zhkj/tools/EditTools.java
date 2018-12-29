package com.zhkj.tools;

import com.zhkj.log.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: FYL
 * @time: 2018/12/4
 * @email:347933430@qq.com
 * @describe: com.zhkj.tools
 */
public class EditTools {

    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static Map<String,String> fields = new HashMap<>();//保存字段集合
    private static Map<String,String> methods = new HashMap<>();//方法名集合
    private static Map<String,String> widgets = new HashMap<>();//控件名集合
    private static Map<String,String> images = new HashMap<>();//控件名集合

    private static String clazzName;//修改前的类字段名
    private static String editClazzName;//修改后的类字段名

    private static String[] specialKey  = null;

    /**
     * 获取控件名称,并修改
     * @param line
     * @return 返回修改后的行
     */
    public static String getWidgetValue(String line) {
        String tmp = line;
        String widgetRegex = "(\\s*this\\.\\s*)(\\w+)(\\s*\\=null;)";
        Matcher matcher = getMatcher(line,widgetRegex);
        if(matcher.find()){
            String random = getRandomString(getRandomInt(4,6));//随机生成4-6位控件名称
            tmp = matcher.group(1)+random+matcher.group(3);
            widgets.put(matcher.group(2),random);//保存控件名称和对应的随机值
            Log.appendInfo("控件名称>>>"+matcher.group(2)+">>>修改成>>>"+random);
        }
        //替换其他地方引用这个控件名称
        tmp = EditTools.replace(line,widgets);
        return tmp;
    }

    /**
     * 修改图片文件名称
     * @param file 图片源文件路径
     */
    public static void editImageName(File file) {
        String imgeName = file.getName();//得到文件名称
        String imageRegex = "(.*)(\\.jpg|\\.png|\\.gif|\\.mp3|\\.wav)$";
        Matcher matcher = getMatcher(imgeName,imageRegex);
        if(matcher.find()){
            if(isSpecialKey(matcher.group(1))){ //是关键字，不去做修改
                Log.appendInfo("名称>>>"+matcher.group(1)+":关键字,不做修改");
                return;
            }
            String random = getRandomString(getRandomInt(4,11));//随机生成4-6位控件名称
            String newImageName = random+matcher.group(2);//得到后缀,并且拼接成新的文件
            File newFile = new File(file.getParent()+"/"+newImageName);
            boolean isEdit = file.renameTo(newFile);
            if(isEdit){
                images.put(matcher.group(1),random);//保存至集合里面
            }
            Log.appendInfo("修改图片结果："+isEdit+">>>名称>>>"+imgeName+">>>修改成>>>"+newImageName);
        }else{
//            Log.appendInfo("名称>>>"+imgeName+":不是图片");
        }
    }

    /**
     * 修改场景文件
     * @param line
     * @return
     */
    public static String editScene(String line){
        line = replace(line,methods);
        line = replace(line,fields);
        return line;
    }
    /**
     * 是否是.mete文件
     * @param file
     * @return
     */
    public static boolean isMetaFile(File file){
        String metaName = file.getName();//得到文件名称
        String meteRegex = "(.*\\.)(meta)$";
        Matcher matcher = getMatcher(metaName,meteRegex);
        if(matcher.find()){
            return true;
        }
        return false;
    }
    /**
     * 是否是.fire文件
     * @param file
     * @return
     */
    public static boolean isFireFile(File file){
        String metaName = file.getName();//得到文件名称
        String meteRegex = "(.*\\.)(fire)$";
        Matcher matcher = getMatcher(metaName,meteRegex);
        if(matcher.find()){
            return true;
        }
        return false;
    }

    /**
     * 修改图片.meta文件
     * @param fileName
     * @return
     */
    public static String editFileName(String fileName){
        String tmp = fileName;
        String meteRegex = "([\\w_@]+)(\\.jpg|\\.png|\\.mp3|\\.wav)(\\.meta)$";
        Matcher matcher = getMatcher(fileName,meteRegex);
        if(matcher.find()){
            if(null!=images&&images.size()>0){
                for (Map.Entry<String, String> entry : images.entrySet()) {
                    if(matcher.group(1).equals(entry.getKey())){
                        tmp = entry.getValue()+matcher.group(2)+matcher.group(3);
                        break;
                    }
                }
            }

        }
        return tmp;
    }

    /**
     * 开启替换，修改
     * @param tmp
     * @return
     */
    public static String editMeta(String tmp){
        if(null!=images&&images.size()>0){
            tmp = replace(tmp,images);
        }
        return tmp;
    }

    /**
     * 修改场景文件里面调用图片和控件的地方
     * @param line
     */
    public static String editSecnce(String line) {
        String tmp = line;
        if(null!=widgets&&widgets.size()>0){
            tmp = replace(tmp,widgets);
        }
        if(null!=images&&images.size()>0){
            tmp = replace(tmp,images);
        }
        return tmp;
    }

    public static String editJS(String tmp) {
        //得到类字段名称
        clazzName = getClazzValue(tmp);//得到类字段名称
        tmp = replaceAll(tmp);
        editClazzName = getMapValue(clazzName,fields);
        tmp = insertCode(tmp,editClazzName);//插入无用代码
        tmp = filter(tmp);//过滤注释
        return tmp;
    }

    /**
     * 删除打印日志
     * @param tmp
     * @return
     */
    private static String deleteLog(String tmp) {
        //console.log('OGuSPFP');
        String regex = "(\\s*console.log\\(.*\\);)";
        tmp = tmp.replaceAll(regex,"");
        return tmp;
    }

    public static String editCocosJS(String tmp) {
        String regex2 = "(\\s*var\\s*)(\\bmethod\\b)(\\s*=)";
        Matcher matcher2 = getMatcher(tmp, regex2);
        if(matcher2.find()){
            tmp = "";
            return tmp;
        }
        tmp = replaceAll(tmp);//开始替换
        tmp = insertCocosCode(tmp);//插入无用代码
        tmp = filter(tmp);//过滤注释
        tmp = deleteLog(tmp); //删除打印日志
        return tmp;
    }

    /**
     * 获取类字段
     * @param tmp
     * @return
     */
    private static String getClazzValue(String tmp){
//            var ckLH = Game.prototype;
        String regex = "(\\s*var\\s+)(\\w+)(\\s*\\=\\s*\\w+\\.prototype;\\s*)";
        Matcher matcher = getMatcher(tmp,regex);
        if(matcher.find()){
            if(clazzName==null){
                clazzName = matcher.group(2);
            }
        }
        return clazzName;
    }

    /**
     * 遍历文件得到所有字段 Laya引擎
     * @param file
     */
    public static void getFields(File file) {
        String regex1 = "(\\s*let\\s+)(\\w+)(\\s*;)";
        String regex2 = "(\\s*var\\s+)(\\w+)(\\s*\\=\\s*.*;|\\s*\\=\\s*\\[.*)";
        BufferedReader br = null;
        List<String> lists = new ArrayList<>();
        try{
            //2.创建流
            br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            //3.读取行
            String line = null;
            while((line = br.readLine())!=null) {
                Matcher matcher1 = getMatcher(line,regex1);
                if (matcher1.find()){
                    lists.add(matcher1.group(2));
                }
                Matcher matcher2 = getMatcher(line,regex2);
                if (matcher2.find()){
                    lists.add(matcher2.group(2));
                }
            }
            Set set = new HashSet();
            for (String cd:lists) {
                if(set.add(cd)){ //去除重复的字段
                    if(!isSpecialKey(cd)){ //不是特殊字段才去添加
                        String random = getRandomString(getRandomInt(4,8));//随机生成4-6位控件名称
                        fields.put(cd,random);
                        Log.appendInfo("字段名>>>"+cd+">>>修改成>>>"+random);
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(br!=null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 遍历文件得到所有字段 Cocos引擎
     * @param file
     */
    public static void getCocosFields(File file) {
        //let upPillarDownY = spaceCenterY + this.EvRlYS / 2;
        String regex1 = "(\\s*let\\s+)(\\w+)(\\s*\\=\\s*.*;)";
        String regex2 = "(\\s*var\\s+)(\\w+)(\\s*\\=\\s*.*;|\\s*\\=\\s*\\[.*)";
        String regex3 = "(\\s*)(\\w+)(\\s*:\\s*\\{\\s*)";//        scoreLabel: {
        BufferedReader br = null;
        List<String> lists = new ArrayList<>();
        try{
            //2.创建流
            br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            //3.读取行
            String line = null;
            while((line = br.readLine())!=null) {
                Matcher matcher1 = getMatcher(line,regex1);
                if (matcher1.find()){
                    lists.add(matcher1.group(2));
                    Log.appendInfo("字段行："+line+"字段关键词："+matcher1.group(2));
                }
                Matcher matcher2 = getMatcher(line,regex2);
                if (matcher2.find()){
                    lists.add(matcher2.group(2));
                    Log.appendInfo("字段行："+line+"字段关键词："+matcher2.group(2));
                }
                Matcher matcher3 = getMatcher(line,regex3);
                if (matcher3.find()){
                    lists.add(matcher3.group(2));
                    Log.appendInfo("字段行："+line+"字段关键词："+matcher3.group(2));
                }
            }
            Set set = new HashSet();
            for (String cd:lists) {
                if(set.add(cd)){ //去除重复的字段
                    if(!isSpecialKey(cd)){ //不是特殊字段才去添加
                        String random = getRandomString(getRandomInt(4,8));//随机生成4-6位控件名称
                        fields.put(cd,random);
                        Log.appendInfo("字段名>>>"+cd+">>>修改成>>>"+random);
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(br!=null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 遍历文件得到所有方法 Cocos引擎
     * @param file
     */
    public static void getCocosMethods(File file) {
        BufferedReader br = null;
        List<String> lists = new ArrayList<>();
        try{
            //2.创建流
            br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            //3.读取行
            String line = null;
            while((line = br.readLine())!=null) {
                String regex = "(\\s*var\\s*method\\s*\\=\\s*\\[)(.*)(\\];)";
                Matcher matcher = EditTools.getMatcher(line,regex);
                if(matcher.find()){
                    String me = matcher.group(2);
                    String met[] = me.split(",");
                    for(int i = 0;i<met.length;i++){
                        String method = met[i].substring(1,met[i].length()-1);
//                        System.out.println("方法名："+method);
                        lists.add(method);
                    }
                }
            }
            Set set = new HashSet();
            for (String cd:lists) {
                if(set.add(cd)){ //去除重复的方法
                    if(!isSpecialKey(cd)){ //不是特殊方法才去添加
                        String random = getRandomString(getRandomInt(4,8));//随机生成4-6位控件名称
                        methods.put(cd,random);
                        Log.appendInfo("方法名>>>"+cd+">>>修改成>>>"+random);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(br!=null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 遍历文件得到所有方法  Laya引擎
     * @param file
     */
    public static void getMethods(File file) {
        String regex1 = "(\\s*\\w+\\s*\\.\\s*)(\\w+)(\\s*\\=\\s*function\\s*\\(.*\\)\\s*\\{\\s*)";
        String regex2 = "(\\s*function\\s+)(\\w+)(\\s*\\(\\w*\\)\\s*\\{)";
        String regex2_1 = "(\\b[A-Z][a-zA-Z]*\\s*)";
        BufferedReader br = null;
        List<String> lists = new ArrayList<>();
        try{
            //2.创建流
            br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            //3.读取行
            String line = null;
            while((line = br.readLine())!=null) {
                Matcher matcher1 = getMatcher(line,regex1);
                if (matcher1.find()){
                    lists.add(matcher1.group(2));
                }
                Matcher matcher2 = getMatcher(line,regex2);
                if (matcher2.find()){
                    Pattern pa = Pattern.compile(regex2_1);
                    Matcher ma = pa.matcher(matcher2.group(2));
                    if(!ma.find()){ //开头首字母是大写，默认认为是构造方法，不保存添加做修改
                        lists.add(matcher2.group(2));
                    }
                }
            }
            Set set = new HashSet();
            for (String cd:lists) {
                if(set.add(cd)){ //去除重复的方法
                    if(!isSpecialKey(cd)){ //不是特殊方法才去添加
                        String random = getRandomString(getRandomInt(4,8));//随机生成4-6位控件名称
                        methods.put(cd,random);
                        Log.appendInfo("方法名>>>"+cd+">>>修改成>>>"+random);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(br!=null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 全局替换
     * @param line
     */
    public static String replaceAll(String line){
        String tmp = line;
        if(null!=images&&images.size()>0){
            tmp = replaceImage(tmp,images);
        }
        if(null!=fields&&fields.size()>0){
            tmp = replace(tmp,fields);
        }
        if(null!=widgets&&widgets.size()>0){
            tmp = replace(tmp,widgets);
        }
        if(null!=methods&&methods.size()>0){
            tmp = replace(tmp,methods);
        }
        return tmp;
    }

    /**
     * 根据key得到value
     * @param methods
     * @return
     */
    private static String getMapValue(String methods,Map<String,String> map) {
        if(null==map||map.size()<=0){return null;}
        if(null==methods||"".equals(methods)){return null;}
        String mapMethod = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String method = entry.getKey();
            if(methods.equals(method)){
                mapMethod = entry.getValue();
                break;
            }
        }
        return mapMethod;
    }
    /**
     * 替换字段名,控件名，方法名
     * @param line
     * @return
     */
    private static String replace(String line,Map<String,String> maps){
        String tmp = line;
        for (Map.Entry<String, String> entry : maps.entrySet()) {
                String re = "(\\b"+entry.getKey()+"\\b)";
                tmp =  tmp.replaceAll(re,entry.getValue());
                Matcher matcher = getMatcher(line,re);
                if(matcher.find()){
                    Log.appendInfo("##"+line+">>> ##替换的关键字>>"+entry.getKey());
                }
        }
        return tmp;
    }

    private static String replaceImage(String line,Map<String,String> maps){
        String tmp = line;
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            String regex = "("+entry.getKey()+")(\\.jpg|\\.png|\\.gif|\\.mp3|\\.wav)";
            Matcher matcher = getMatcher(line,regex);
            if(matcher.find()){
                String re = "(\\b"+entry.getKey()+"\\b)";
                tmp =  tmp.replaceAll(re,entry.getValue());
                Matcher ma = getMatcher(line,re);
                if(ma.find()){
                    Log.appendInfo("##"+line+">>> ##替换的关键字>>"+entry.getKey());
                }
            }
        }
        return tmp;
    }

    /**
     * 插入无用的代码
     * @param line
     * @return
     */
    public static String insertCode(String line,String newClazzName) {
        String regex = "(.*?)(\\/\\*\\*.*?\\*\\/)";
        Matcher matcher = getMatcher(line, regex);
        StringBuffer sb = new StringBuffer();
        String tmp = line;
        while (matcher.find()){
            if((matcher.group(2).equals("/**#1*/"))){
                //生成1-3条无用代码
                tmp = getRandomCode(getRandomInt(1, 3));
                matcher.appendReplacement(sb, matcher.group(1)+"\r\n"+tmp);
            }else if((matcher.group(2).equals("/**#2*/"))){
                //生成无用方法
                tmp = getRandomNoCode(newClazzName);
                matcher.appendReplacement(sb, matcher.group(1)+"\r\n"+tmp);
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    /**
     * 插入无用的代码 Cocos引擎
     * @param line
     * @return
     */
    public static String insertCocosCode(String line) {
        String regex = "(.*?)(\\/\\*\\*.*?\\*\\/)";
        Matcher matcher = getMatcher(line, regex);
        StringBuffer sb = new StringBuffer();
        String tmp = line;
        while (matcher.find()){
            if((matcher.group(2).equals("/**#1*/"))){
                //生成1-3条无用代码
                tmp = getRandomCode(getRandomInt(1, 8));
                matcher.appendReplacement(sb, matcher.group(1)+"\r\n"+tmp);
            }else if((matcher.group(2).equals("/**#2*/"))){
                //生成无用方法
                tmp = getRandomCocosNoCode();
                matcher.appendReplacement(sb, matcher.group(1)+tmp+"\r\n");
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 过滤注释
     * @param line
     */
    public static String filter(String line) {
        //过滤/***/注释
        String regex = "(.*?)(\\/\\*\\*.*?\\*\\/)";
        Matcher matcher = getMatcher(line, regex);
        StringBuffer sb = new StringBuffer();
        String tmp = line;
        while (matcher.find()){
            if((matcher.group(2).equals("/**#0*/"))||(matcher.group(2).equals("/**#3*/"))||(matcher.group(2).equals("/**#4*/"))||(matcher.group(2).equals("/**#5*/"))){
                matcher.appendReplacement(sb, matcher.group(1));
            }
        }
        matcher.appendTail(sb);
        tmp = sb.toString();
        //过滤//注释
        String regex1 = "(\\s*\\/\\/\\s*)";
        Matcher matcher1 = getMatcher(tmp, regex1);
        if(matcher1.find()){
            if(tmp.startsWith("//")){
                tmp = "";
            }
            int index = tmp.indexOf("//");
            if(index>0){
                String substring = tmp.substring(index - 1, index);
                if(!substring.equals(":")&&!substring.equals("*")){
                    tmp = tmp.substring(0, index);
                }
            }

        }
        //[\r\n]/g
        tmp = tmp.replaceAll("[\\r\\n\\t]","");
        return tmp;
    }

    /**
     * 判断是否是特殊字段
     * @param key
     */
    private static boolean isSpecialKey(String key){
        if(null==key||key.equals("")){return true;}
        boolean isSpecialKey=false;
        for(int i = 0;i<specialKey.length;i++){
            if(key.equals(specialKey[i])){
                isSpecialKey = true;
                break;
            }
        }
        return isSpecialKey;
    }

    public static Matcher getMatcher(String line,String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        return matcher;
    }

    public static void setSpecialKey(String [] arr){
        specialKey=arr;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<specialKey.length;i++){
            sb.append(specialKey[i]+",");
        }
        System.out.println("关键字："+sb.toString());
    }
    /**
     * 生成无用方法
     * Laya适用
     * @param menthodKey 方法头
     */
    public static String getRandomNoCode(String menthodKey) {
        StringBuilder str = new StringBuilder();
        int randomNumber = getRandomInt(2, 10);
        str.append(menthodKey + ModeRule.D);//_proto.
        str.append(getRandomString(randomNumber));//方法名
        str.append(" = function () { " + "\r\n");
        str.append(getRandomCode());//插入方法内容
        str.append("}\r\n");

        return str.toString();
    }
    /**
     * 生成无用方法
     * Cocos适用
     */
    public static String getRandomCocosNoCode() {
        StringBuilder str = new StringBuilder();
        int randomNumber = getRandomInt(3, 8);
        str.append(getRandomString(randomNumber));//方法名
        // restartGame: function () {
        str.append(" : function () { " + "\r\n");
        str.append(getRandomCode());//插入方法内容
        str.append("},\r\n");

        return str.toString();
    }

    /**
     * 生成随机字符串 大小写混合
     *
     * @param length 生成字符串的个数
     * @return
     */
    public static String getRandomString(int length) {
        return getRandom(LETTERS.toCharArray(), length);
    }

    /**
     * @param min 最小
     * @param max 最大
     * @return 返回一个范围的数值
     */
    public static int getRandomInt(int min, int max) {

        if (min > max) {
            return 0;
        }
        if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }

    /**
     * 生成随机int数组
     *
     * @return
     */
    public static String getRandomIntArray() {
        int randomNumber = getRandomInt(3, 8);
        StringBuilder str = new StringBuilder();
        str.append("var ");
        str.append(getRandomString(randomNumber));
        str.append(" = [");
        for (int i = 1; i <= randomNumber; i++) {
            if (i == randomNumber) {
                str.append(getRandomInt(1, 100));
            } else {
                str.append(getRandomInt(1, 100) + ",");
            }
        }
        str.append("];");
        return str.toString() + "\r\n";
    }

    /**
     * 生成随机String数组
     *
     * @return
     */
    public static String getRandomStringArray() {
        int randomNumber = getRandomInt(3, 8);
        StringBuilder str = new StringBuilder();
        str.append("var ");
        str.append(getRandomString(randomNumber));
        str.append(" = [");
        for (int i = 1; i <= randomNumber; i++) {
            if (i == randomNumber) {
                str.append('"' + getRandomString(randomNumber) + '"');
            } else {
                str.append('"' + getRandomString(randomNumber) + '"' + ",");
            }
        }
        str.append("];");
        return str.toString() + "\r\n";
    }

    /**
     * 生成随机Char数组
     *
     * @return
     */
    public static String getRandomCharArray() {
        int randomNumber = getRandomInt(3, 8);
        StringBuilder str = new StringBuilder();
        str.append("var ");
        str.append(getRandomString(randomNumber));
        str.append(" = [");
        for (int i = 1; i <= randomNumber; i++) {
            if (i == randomNumber) {
                str.append("'" + getRandomString(randomNumber) + "'");
            } else {
                str.append("'" + getRandomString(randomNumber) + "',");
            }
        }
        str.append("];");
        return str.toString() + "\r\n";
    }

    /**
     * 生成随机定义int类型代码
     *
     * @return
     */
    public static String getRandomInt() {
        int randomNumber = getRandomInt(3, 8);
        StringBuilder str = new StringBuilder();
        str.append("var ");
        str.append(getRandomString(randomNumber));
        str.append(" = ");
        str.append(getRandomInt(0, 9));
        str.append(";");
        return str.toString() + "\r\n";
    }

    /**
     * 生成随机定义String类型代码
     *
     * @return
     */
    public static String getRandomString() {
        int randomNumber = getRandomInt(3, 8);
        StringBuilder str = new StringBuilder();
        str.append("var ");
        str.append(getRandomString(randomNumber));
        str.append(" = " + "'");
        str.append(getRandomString(randomNumber));
        str.append("'" + ";");
        return str.toString() + "\r\n";
    }

    /**
     * 生成随机定义double类型代码
     *
     * @return
     */
    public static String getRandomDouble() {
        int randomNumber = getRandomInt(3, 8);
        StringBuilder str = new StringBuilder();
        str.append("var ");
        str.append(getRandomString(randomNumber));
        str.append(" = ");
        str.append(getRandomInt(0, 9) + "." + getRandomInt(0, 9));
        str.append(";");
        return str.toString() + "\r\n";
    }

    /**
     * 生成随机定义bollean类型代码
     *
     * @return
     */
    public static String getRandomBoolean() {
        int randomNumber = getRandomInt(3, 8);
        StringBuilder str = new StringBuilder();
        str.append("var ");
        str.append(getRandomString(randomNumber));
        str.append(" = ");
        int random = getRandomInt(0, 9);
        if (random % 2 == 0) {
            str.append("true");
        } else {
            str.append("false");
        }
        str.append(";");
        return str.toString() + "\r\n";
    }

    /**
     * 生成随机定义float类型代码
     *
     * @return
     */
    public static String getRandomFloat() {
        int randomNumber = getRandomInt(3, 8);
        StringBuilder str = new StringBuilder();
        str.append("var ");
        str.append(getRandomString(randomNumber));
        str.append(" = ");
        str.append(getRandomInt(1, 9) + "." + getRandomInt(1, 9));
        str.append(";");
        return str.toString() + "\r\n";
    }

    /**
     * 生成随机定义对象类型代码
     *
     * @return
     */
    public static String getRandomObject() {
        int randomNumber = getRandomInt(3, 8);
        StringBuilder str = new StringBuilder();
        str.append("var ");
        str.append(getRandomString(randomNumber));
        str.append(" = {");
        for (int i = 0; i < randomNumber; i++) {
            str.append(getRandomString(randomNumber) + ":" + getRandomObject(getRandomInt(0, 3)) + ",");
        }
        str.append("};");
        return str.toString() + "\r\n";
    }

    private static Object getRandomObject(int random) {
        switch (random) {
            case 0:
                return "'" + getRandomString(4) + "'";//返回随机String
            case 1:
                return getRandomInt(0, 9);
            case 2:
                return getRandomInt(0, 9) % 2 == 0 ? true : false;
        }
        return "0";
    }

    private static String getRandom(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) {
            return null;
        }
        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(sourceChar[random.nextInt(sourceChar.length)]);
        }
        return str.toString();
    }

    /**
     * 随机从9种无用代码中产生
     *
     * @param number //产生几条无用代码
     * @return
     */
    public static String getRandomCode(int number) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < number; i++) {
            str.append(getRandomCode());
        }
        return str.toString();
    }

    /**
     * 随机从9中无用代码中产生
     *
     * @return
     */
    public static String getRandomCode() {
        int random = getRandomInt(0, 9);
        switch (random) {
            case 0:
                return getRandomString();
            case 1:
                return getRandomInt();
            case 2:
                return getRandomFloat();
            case 3:
                return getRandomDouble();
            case 4:
                return getRandomBoolean();
            case 5:
                return getRandomObject();
            case 6:
                return getRandomIntArray();
            case 7:
                return getRandomStringArray();
            case 8:
                return getRandomCharArray();
            default:
                return getRandomString();
        }

    }



}
