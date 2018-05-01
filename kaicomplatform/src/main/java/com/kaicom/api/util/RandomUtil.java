package com.kaicom.api.util;

import java.util.Random;

/**
 * <h3>随机数工具类</h3>
 * <p>方便用于制造测试数据
 * @author scj
 *
 */
public final class RandomUtil {

    public static final String NUMBERS_AND_LETTERS = "0123456789"
            + "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS = "0123456789";
    public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

    private RandomUtil() {
        throw new RuntimeException("…（⊙＿⊙；）…");
    }
    
    /**
     * 获得一个长度为length，由数字和字母随机组成的字符串
     * @param length
     * @return
     */
    public static String getRandomNumbersAndLetters(int length) {
        return getRandom(NUMBERS_AND_LETTERS, length);
    }

    /**
     * 获得一个长度为length，由数字随机组成的字符串
     * @param length 字符串长度
     * @return
     */
    public static String getRandomNumbers(int length) {
        return getRandom(NUMBERS, length);
    }

    /**
     * 获得一个长度为length,由大小字母随机组成的字符串
     * @param length 字符串长度
     * @return
     */
    public static String getRandomLetters(int length) {
        return getRandom(LETTERS, length);
    }

    /**
     * 随机获得一个长度为length的大写字符串
     * 
     * @param length 字符串长度
     * @return
     */
    public static String getRandomCapitalLetters(int length) {
        return getRandom(CAPITAL_LETTERS, length);
    }

    /**
     * 随机获得一个长度为length的小写字符串
     * 
     * @param length 字符串长度
     * @return
     */
    public static String getRandomLowerCaseLetters(int length) {
        return getRandom(LOWER_CASE_LETTERS, length);
    }

    /**
     * 从一个字符串送随机获得一个长度为length的字符串
     * 
     * @param source 源字符串
     * @param length 获得字符串的长度
     * @return 如果source为null或空串，则返回null
     */
    public static String getRandom(String source, int length) {
        if (source == null || source.length() == 0)
            return null;
        return getRandom(source.toCharArray(), length);
    }

    /**
     * 从字符数组中随机获得一个长度为length字符串
     * 
     * @param sourceChar 源数组
     * @param length 字符串长度
     * @return
     * <li>如果字符数组为null或空，返回null
     * <li>如果length小于0，返回null
     * <li>否则返回一个长度为length的字符串
     */
    public static String getRandom(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) {
            return null;
        }
        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(sourceChar[random.nextInt(sourceChar.length)]);
        }
        return str.toString();
    }

    /**
     * 获得一个随机数字（在0到某个数之间）
     * 
     * @param max
     * @return <ul>
     * <li>如果0小于等于 max，返回0</li>
     * <li>否则返回0到max之间的一个随机数字</li>
     * </ul>
     */
    public static int getRandom(int max) {
        return getRandom(0, max);
    }

    /**
     * 获得一个随机数字（在某个范围内）
     * 
     * @param min 最小值
     * @param max 最大值
     * @return <ul>
     * <li>如果min > max，返回0</li>
     * <li>如果min = max，返回min</li>
     * <li>否则，返回从min到max之间的一个随机数字</li>
     * </ul>
     */
    public static int getRandom(int min, int max) {
        if (min > max) {
            return 0;
        }
        if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }
    
    /**
     * 从指定数组中随机获取元素(不包含结束位置)
     * @param src
     * @param start 开始位置
     * @param end 结束位置
     * @return
     */
    public static <T> T getRandomFrom(T[] src, int start, int end) {
    	if (start < 0 || end < 0)
    		return null;
    	return src[getRandom(start, end)];
    }
    
    /**
     * 从指定数组中随机获取元素
     * @param src
     * @return
     */
    public static <T> T getRandomFrom(T[] src) {
    	int len = src.length;
    	return src[getRandom(len)];
    }
    
    /**
     * 洗牌算法，随机交换intArray中的值
     * 
     * @param intArray int类型数组
     * @return
     */
    public static int[] shuffle(int[] intArray) {
        if (intArray == null) {
            return null;
        }
        return shuffle(intArray, getRandom(intArray.length));
    }

    /**
     * 洗牌算法，从intArray中的获得一个长度为shuffleCount的新数组
     * 
     * @param intArray
     * @param shuffleCount
     * @return <ul>
     * <li>如果intArray为null，返回null</li>
     * <li>如果shuffleCount小于0，返回null</li>
     * <li>如果intArray的长度小于shuffleCount，返回null</li>
     * </ul>
     */
    public static int[] shuffle(int[] intArray, int shuffleCount) {
        int length;
        if (intArray == null || shuffleCount < 0
                || (length = intArray.length) < shuffleCount) {
            return null;
        }
        int[] out = new int[shuffleCount];
        for (int i = 1; i <= shuffleCount; i++) {
            int random = getRandom(length - i);
            out[i - 1] = intArray[random];
            int temp = intArray[length - i];
            intArray[length - i] = intArray[random];
            intArray[random] = temp;
        }
        return out;
    }

    /**
     * 洗牌算法，随机交换数组中的值
     * @param <T>
     * 
     * @param array 数组
     * @return
     */
    public static <T> T[] shuffle(T[] array) {
        if (array == null) {
            return null;
        }
        return shuffle(array, array.length);
    }
    
    /**
     * 洗牌算法，从泛型数组中的获得一个长度为shuffleCount的新数组
     * @param <T>
     * 
     * @param array
     * @param shuffleCount
     * @return <ul>
     * <li>如果array为null，返回null</li>
     * <li>如果shuffleCount小于0，返回null</li>
     * <li>如果array的长度小于shuffleCount，返回null</li>
     * </ul>
     */
    public static <T> T[] shuffle(T[] array, int shuffleCount) {
        int length;
        if (array == null || shuffleCount < 0
                || (length = array.length) < shuffleCount) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T[] out = (T[]) new Object[shuffleCount];
        for (int i = 1; i <= shuffleCount; i++) {
            int random = getRandom(length - i);
            out[i - 1] = array[random];
            T temp = array[length - i];
            array[length - i] = array[random];
            array[random] = temp;
        }
        return out;
    }

}
