package com.kaicom.api.log;

import java.util.List;

/**
 * <h3>日志工具类</h3>
 * 
 * <p>主要封装了一些对日志的读写操作 ，注：写文件通过线程完成，读文件未启用线程。
 * <br>
 * <h5>日志记录:</h5>
 * 包含打印到控制台和写入文件两个方式
 * <h5>日志级别:</h5>
 * <p>目前定义了 info, debug, error三个级别。
 * <li>info     该级别消息只会打印在控制台, 出货版本会被移除
 * <li>debug    该级别消息即会打印在控制台, 也会写文件（通过开关控制，可写可不写）
 * <li>error    该级别消息即会打印在控制台, 也会写文件（不可控制）
 * 
 * <h5>TAG:</h5>
 * 可设置LogConfig中的默认tag, 也可直接调用传tag的方法
 * 
 * @author scj
 */
public final class KlLoger {

    private static LogManager logManager;

    static {
        logManager = LogManager.getLogger();
    }

    private KlLoger() {
    }

    /**
     * 在Logcat打印info级别信息
     * <br>
     * 默认为config中的TAG
     * @param message 消息内容
     */
    public static void info(String message) {
        info(logManager.config.tag, message);
    }

    /**
     * 在Logcat打印info级别信息
     * @param pTag TAG
     * @param message 消息内容
     */
    public static void info(String pTag, String message) {
        info(pTag, message, null);
    }

    private static void info(String pTag, String message, Throwable e) {
        LogInfo data = new LogInfo();
        data.setLevel(Level.INFO);
        data.setTag(pTag);
        data.setMessage(message);
        data.setThrowableInfo(e);
        logManager.info(data);
    }

    /**
     * 在Logcat打印debug级别信息, 在debug模式会将信息写入文件
     * <br>
     * 默认为config中的TAG
     * @param message 消息内容
     */
    public static void debug(String message) {
        debug(logManager.config.tag, message, null);
    }

    /**
     * 在Logcat打印debug级别信息, 在debug模式会将信息写入文件
     * @param pTag TAG
     * @param message 消息内容
     */
    public static void debug(String pTag, String message) {
        debug(pTag, message, null);
    }

    /**
     * 在Logcat打印debug级别信息, 在debug模式会将信息写入文件
     * <br>
     * 默认为config中的TAG
     * @param message 消息内容
     * @param e 异常
     */
    public static void debug(String message, Throwable e) {
        debug(logManager.config.tag, message, e);
    }

    /**
     * 在Logcat打印debug级别信息, 在debug模式会将信息写入文件
     * @param pTag TAG
     * @param message 消息内容
     * @param e 异常
     */
    public static void debug(String pTag, String message, Throwable e) {
        LogInfo data = new LogInfo();
        data.setLevel(Level.DEBUG);
        data.setTag(pTag);
        data.setMessage(message);
        data.setThrowableInfo(e);
        logManager.debug(data);
    }

    /**
     * 在Logcat打印error级别信息, 并将信息写入文件
     * <br>
     * 默认为config中的TAG
     * @param message 消息内容
     */
    public static void error(String message) {
        error(logManager.config.tag, message, null);
    }

    /**
     * 在Logcat打印error级别信息, 并将信息写入文件
     * <br>
     * 默认为config中的TAG
     * @param pTag TAG
     * @param message 消息内容
     */
    public static void error(String pTag, String message) {
        error(pTag, message, null);
    }

    /**
     * 在Logcat打印error级别信息, 并将信息写入文件
     * <br>
     * 默认为config中的TAG
     * @param message 消息内容
     * @param e 异常
     */
    public static void error(String message, Throwable e) {
        error(logManager.config.tag, message, e);
    }

    /**
     * 在Logcat打印error级别信息, 并将信息写入文件
     * @param pTag TAG
     * @param message 消息内容
     * @param e 异常
     */
    public static void error(String pTag, String message, Throwable e) {
        LogInfo data = new LogInfo();
        data.setLevel(Level.ERROR);
        data.setTag(pTag);
        data.setMessage(message);
        data.setThrowableInfo(e);
        logManager.error(data);
    }

    /**
     * 读取指定的日志文件<br>
     * 注：读文件操作在主线程中完成
     * @param filePath 文件绝对路径
     * @return 日志文件中的内容
     */
    public static String readLog(String filePath) {
        return logManager.read(filePath);
    }

    /**
     * 获取客户日志目录下的日志文件列表
     * @param dirPath 目录
     */
    public static List<String> getFileList() {
        return logManager.getFileList();
    }

    /**
     * 获取指定目录下的日志文件列表
     * @param dirPath 目录
     * @return 目录下的日志文件名
     */
    public static List<String> getFileList(String dirPath) {
        return logManager.getFileList(dirPath);
    }
    
    /**
     * 删除日志文件
     * @param fileName 文件名
     * @return 删除成功返回true
     */
    public static boolean delete(String fileName) {
        return logManager.delete(fileName);
    }
    
    /**
     * 删除默认日志目录
     * @return
     */
    public static boolean deleteDir() {
        return logManager.deleteDir();
    }
    
    /**
     * 获得日志目录
     * @return
     */
    public static String getDirPath() {
        return logManager.config.fileDir;
    }
    
    /**
     * 自动清理日志
     */
    public static void autoClear() {
        logManager.autoClear();
    }

}
