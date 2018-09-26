package rapid.com.tools;

/**
 * @author: FYL
 * @time: 2018/9/3
 * @email:347933430@qq.com
 * @describe: EventBus封装bean
 */
public class EventBusEvent<T> {
    //区别码,当有多个广播的时候区分
    private int code;
    //携带的数据
    private T data;
    //当使用列表的时候用到的index下标,没用可以不用管
    private int position;

    public EventBusEvent(int code) {
        this.code = code;
    }

    public EventBusEvent(int code, T data) {
        this.code = code;
        this.data = data;
    }
    public EventBusEvent(int code, int position, T data) {
        this.code = code;
        this.position = position;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
