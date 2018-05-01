package com.kaicom.api.net.spi.request;

public interface Request<T> {
    
    /**
     * <h5>请求优先级</h5>
     * 由低到高一共分为四级，优先级高的请求先发送
     * 
     * @author scj
     *
     */
    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }
    
}
