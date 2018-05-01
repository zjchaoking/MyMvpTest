/*
 *
 */

package com.kaicom.api.network.event;

/**
 * Event的工具类，用于生成事件号，是线程安全的.
 * <p>
 */
public class EventUtils {
    /**
     * 单例控制.
     */
    private static EventUtils instance = null;

    static {
        // 生成全局唯一的单实例
        instance = new EventUtils();
    }

    /**
     * 获取单实例.
     * 
     * @return 单实例
     */
    public static EventUtils getInstance() {
        return instance;
    }
/**
 * EventUtils.
 */
    private EventUtils() {
        lock = new byte[0];
    }

    /** 计数锁，保证线程安全. */
    private byte[] lock = new byte[0];
    /** 计数器 .*/
    private int iMessageId = 500;

    /**
     * 生成事件号.
     * 
     * @return 事件号
     */
    public int generateEventNo() {
        synchronized (lock) {
            if (iMessageId == Integer.MAX_VALUE) {
                iMessageId = 500;
            } else {
                ++iMessageId;
            }
            return iMessageId;
        }
    }

}
