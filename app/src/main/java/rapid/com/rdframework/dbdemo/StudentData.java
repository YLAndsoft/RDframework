package rapid.com.rdframework.dbdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: FYL
 * @time: 2018/9/17
 * @email:347933430@qq.com
 * @describe: rapid.com.rdframework.dbdemo
 */
public class StudentData {

    public static List<Student> getDBData(){
        List<Student> lists = new ArrayList<>();
        lists.add(new Student("张三",0,22,"苏州","2018-08-10 10:00:00"));
        lists.add(new Student("李四",0,23,"重庆","2018-08-12 11:00:00"));
        lists.add(new Student("张雪",1,21,"上海","2018-08-11 12:00:00"));
        lists.add(new Student("王二",0,21,"四川","2018-08-13 13:00:00"));
        return lists;
    }

}
