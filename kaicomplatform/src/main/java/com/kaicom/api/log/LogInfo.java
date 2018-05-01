package com.kaicom.api.log;

/**
 * 日志实体
 * <br>
 * 包含日志级别、tag、message、异常
 * @author scj
 */
public class LogInfo {

    private Level level;
    private String tag;
    private String message;
    private Throwable throwableInfo;

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowableInfo() {
        return throwableInfo;
    }

    public void setThrowableInfo(Throwable throwableInfo) {
        this.throwableInfo = throwableInfo;
    }

    public boolean noThrowable() {
        return this.throwableInfo == null;
    }

    @Override
    public String toString() {
        return "LogInfo [level=" + level + ", tag=" + tag + ", message="
                + message + ", throwableInfo=" + throwableInfo + "]";
    }

}
