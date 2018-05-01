/**
 * <p>文件介绍信息</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: 恒生电子股份有限公司</p>
 * <p>Project: 投资赢家移动理财终端5.0</p>
 * @author
 * @version
 * @history
 */
package com.kaicom.api.network.event;

import com.kaicom.api.network.interfaces.INetworkEvent;

/**
 * EventFactory.
 * 
 * @author qiyp
 * @version
 */
public class EventFactory {
    /**
     * 获取网络事件.
     * 
     * @return 获取事件
     */
    public static INetworkEvent getEvent() {
		
        return new CommonEvent();
    }
}
