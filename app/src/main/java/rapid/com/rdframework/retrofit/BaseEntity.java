package rapid.com.rdframework.retrofit;

/**
 * @author: FYL
 * @time: 2018/10/18
 * @email:347933430@qq.com
 * @describe: rapid.com.net
 */
public class BaseEntity<T> {
    private static int SUCCESS_CODE=200;//成功的code
    private int code;
    private String msg;
    private T data;

    public boolean isSuccess(){
        return getCode()==SUCCESS_CODE;
    }
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public T getData() {
        return data;
    }

}
