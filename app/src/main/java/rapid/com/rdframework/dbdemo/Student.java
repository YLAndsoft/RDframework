package rapid.com.rdframework.dbdemo;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.Date;

import rapid.com.tools.ComparableTools;
import rapid.com.tools.TimeTools;

/**
 * @author: FYL
 * @time: 2018/9/17
 * @email:347933430@qq.com
 * @describe: rapid.com.rdframework.dbdemo
 */
@Table(name = "student")//表名
public class Student extends ComparableTools implements Serializable {
    /**
     * isId = true 代表该字段是这张表的主键，类型也可以是String (赋值为false就不是主键了)
     * autoGen = true 代表主键自增长，如果不是自增长，那就赋值false
     */
    @Column(name = "_ID",property = "NOT NULL",isId = true, autoGen = true)
    private int _id;
    @Column(name = "USERNAME",property = "NOT NULL")
    private String userName;
    @Column(name = "USERSEX",property = "NOT NULL")
    private int userSex;
    @Column(name = "USERAGE",property = "NOT NULL")
    private int userAge;
    @Column(name = "USERAddress",property = "NOT NULL")
    private String userAddress;
    @Column(name = "ADDTIME",property = "NOT NULL")
    private String addTime;//数据添加时间

    @Override
    public int compare(Object o1) {
        Student student = (Student)o1;
        Date data1 = TimeTools.string2Date(student.getAddTime());
        Date data2 = TimeTools.string2Date(this.addTime);
        return data1.compareTo(data2);
    }
    public Student(){}
    public  Student(String userName,int userSex,int userAge,String userAddress,String addTime){
            this.userName = userName;
            this.userSex  =userSex;
            this.userAge = userAge;
            this.userAddress = userAddress;
            this.addTime = addTime;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
