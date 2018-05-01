/**
 * 
 */
package com.kaicom.api.util;

import java.util.regex.Pattern;

/**
 * <h3>校验规则工具类</h3> 常用的数据校验
 * 
 * @author wxf
 * 
 */
public final class PatternUtil {
    
    private PatternUtil() {
        throw new RuntimeException("…（⊙＿⊙；）…");
    } 

    /**
     * 判断是否为纯数字
     * 
     * @param str
     *            要校验的字符串
     * @return true/false
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");

        return pattern.matcher(str).matches();
    }

}
