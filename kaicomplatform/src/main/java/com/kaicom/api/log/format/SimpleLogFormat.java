package com.kaicom.api.log.format;

import android.util.Log;

import com.kaicom.api.KaicomApplication;
import com.kaicom.api.log.Level;
import com.kaicom.api.log.LogInfo;
import com.kaicom.api.util.ApkUtil;
import com.kaicom.api.util.DateUtil;
import com.kaicom.api.util.StringUtil;

import java.util.Date;

/**
 * 简单的log文件格式
 * 
 * @author scj
 */
public class SimpleLogFormat implements LogFormat {

    @Override
    public String formatConsole(LogInfo logInfo) {
        String msg = logInfo.getMessage();
        
        StringBuffer sb = new StringBuffer();
        sb.append(DateUtil.getDateTimeFormat(new Date()));
        sb.append("\n");
        sb.append(" log msg ---> ");
        sb.append(StringUtil.nullToBlank(msg));
        sb.append("\r\n");
        appendLogContent(logInfo, msg, sb);
        return sb.toString();
    }

    @Override
    public String formatOutput(LogInfo logInfo) {
        String tag = logInfo.getTag();
        String msg = logInfo.getMessage();
        Level level = logInfo.getLevel();
        
        StringBuffer sb = new StringBuffer();
        sb.append("<log");
        sb.append(" level=\"");
        sb.append(level);
        sb.append("\" ");
        sb.append("tag=\"");
        sb.append(tag);
        sb.append("\">\n");
        
        sb.append("<time>");
        sb.append(DateUtil.getDateTimeFormat(new Date()));
        sb.append("</time>\n");
        
        sb.append("<ver>");
        sb.append(android.os.Build.MODEL);
        sb.append(",");
        sb.append(android.os.Build.BOOTLOADER);
        sb.append(",");
        sb.append(ApkUtil.getVersionName());
        sb.append(",");
        sb.append(KaicomApplication.kaicomJNIProxy.getMachineCode());
        sb.append("</ver>\n");
        
        sb.append("<msg>");
        sb.append(StringUtil.nullToBlank(msg));
        sb.append("</msg>\n");
        sb.append("\t<data>\n");
        appendLogContent(logInfo, msg, sb);
        sb.append("\t</data>\n");
        sb.append("</log>\n\n");
        return sb.toString();
    }
    
    private void appendLogContent(LogInfo logInfo, String msg, StringBuffer sb) {
        if (logInfo.noThrowable()) {
            Throwable tr = new Throwable(msg);
            // 这里取5是为了正好显示调用KlLoger.error()等方法的类or方法的信息
            StackTraceElement element = tr.getStackTrace()[5];
            sb.append("  \tat ");
            sb.append(element.getClassName());
            sb.append(".");
            sb.append(element.getMethodName());
            sb.append("(");
            sb.append(element.getFileName());
            sb.append(":");
            sb.append(element.getLineNumber());
            sb.append(")\n");
        } else {
            sb.append(Log.getStackTraceString(logInfo.getThrowableInfo()));
        }
    }

}
