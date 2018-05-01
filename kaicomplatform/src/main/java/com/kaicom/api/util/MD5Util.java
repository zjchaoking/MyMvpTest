package com.kaicom.api.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <h3>MD5加密工具类</h3>
 * 用于创建MD5加密后的字符串
 * @author scj
 *
 */
public final class MD5Util {
    
    private MD5Util() {
        throw new RuntimeException("…（⊙＿⊙；）…");
    }
    
    /**
     * 该方法将指定的字符串用MD5算法加密后返回
     * @param s 要加密的字符串
     * @return 加密后的字符串
     */
    public static String getMD5Encoding(String s) {
        byte[] input=s.getBytes(); 
        String output = null; 
        // 声明16进制字母 
        char[] hexChar={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'}; 
        try{ 
            // 获得一个MD5摘要算法的对象 
            MessageDigest md=MessageDigest.getInstance("MD5"); 
            md.update(input); 

            byte[] tmp = md.digest();//获得MD5的摘要结果 
            char[] str = new char[32]; 
            byte b=0; 
            for(int i=0;i<16;i++){ 
                b=tmp[i]; 
                str[2*i] = hexChar[b>>>4 & 0xf];
                str[2*i+1] = hexChar[b & 0xf];
            } 
            output = new String(str); 
        }catch(NoSuchAlgorithmException e){ 
            e.printStackTrace(); 
        } 
        
        return output; 
    }

    public static String encryption(String plain) {
        String re_md5 = new String();

        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            e.update(plain.getBytes());
            byte[] b = e.digest();
            StringBuffer buf = new StringBuffer("");

            for(int offset = 0; offset < b.length; ++offset) {
                int i = b[offset];
                if(i < 0) {
                    i += 256;
                }

                if(i < 16) {
                    buf.append("0");
                }

                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException var7) {
            var7.printStackTrace();
        }
        return re_md5;
    }
}
