package rapid.com.tools;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by DN on 2018/7/9.
 */

/**
 * @author: FYL
 * @time: 2018/9/3
 * @email:347933430@qq.com
 * @describe: EventBus广播相关
 */
public class EventBusTools {
    /**
     * 注册
     * @param subscriber
     */
    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    /**
     * 取消注册
     * @param subscriber
     */
    public static void unregister(Object subscriber) {
        if(EventBus.getDefault().isRegistered(subscriber)){
            EventBus.getDefault().unregister(subscriber);
        }
    }

    /**
     * 发送消息:普通广播
     * @param event
     */
    public static void sendEvent(EventBusEvent event) {
        EventBus.getDefault().post(event);
    }
    /**
     * 发送消息：粘性广播
     * @param event
     */
    public static void sendStickyEvent(EventBusEvent event) {
        EventBus.getDefault().postSticky(event);
    }


}
