package com.kaicom.api.log.format;

import com.kaicom.api.log.LogInfo;

/**
 * 格式化输出日志
 * 
 * @author scj
 */
public interface LogFormat {

    /**
     * 格式化控制台输出
     * @param logInfo
     * @return
     */
    public String formatConsole(LogInfo logInfo);
    
    /**
     * 格式化输出, 可以是普通文本，可以是xml
     * @param logInfo
     * @return
     */
    public String formatOutput(LogInfo logInfo);
    
}
