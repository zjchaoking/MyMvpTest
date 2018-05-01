package com.kaicom.api.biz;

@Deprecated
public class BizException extends Exception {

    private static final long serialVersionUID = -715135555645045204L;

    public BizException() {
        super();
    }

    public BizException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public BizException(String detailMessage) {
        super(detailMessage);
    }

    public BizException(Throwable throwable) {
        super(throwable);
    }
    
}
