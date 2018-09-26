package rapid.com.tools;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author: FYL
 * @time: 2018/9/3
 * @email:347933430@qq.com
 * @describe: base相关
 */
public abstract class ComparableTools implements Comparable{

    /**
     * 排序抽象类
     * @param o1 即将比较的实体类，也可以是属性
     * @return  负数：意味着“o1比o2小”
     *           零：意味着“o1等于o2”
     *           正数:意味着“o1大于o2”
     */
    public abstract int compare(Object o1);
    @Override
    public int compareTo(@NonNull Object o) {
        try {
            return compare(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**使用例子*/
    public class Age extends ComparableTools{
        int age;
        String time;
        @Override
        public int compare(Object o) {
            Age o1 = (Age)o;
            //return this.age-o1.age;//比较int类型
            //时间比较
            Date data1 = TimeTools.string2Date(o1.time);
            Date data2 = TimeTools.string2Date(this.time);
            return data1.compareTo(data2);//比较时间类型
        }
    }
    public void main(String[] args){
        List<Age> list = new ArrayList<>();
        //list.addAll(...);
        //需要排序的地方调用这行代码即可
        Collections.sort(list);
    }

}
