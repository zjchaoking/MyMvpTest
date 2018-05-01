package com.kaicom.api.scan;

import static android.view.KeyEvent.*;
import android.annotation.SuppressLint;

/**
 * PDA按键
 * 
 * @author scj
 */
@SuppressLint("InlinedApi")
public final class KeyCodeConstant {

    private KeyCodeConstant(){}
    
    /** 数字键0 */
    public static final int KEY_0 = KEYCODE_0;
    /** 数字键1 */
    public static final int KEY_1 = KEYCODE_1;
    /** 数字键2 */
    public static final int KEY_2 = KEYCODE_2;
    /** 数字键3 */
    public static final int KEY_3 = KEYCODE_3;
    /** 数字键4 */
    public static final int KEY_4 = KEYCODE_4;
    /** 数字键5 */
    public static final int KEY_5 = KEYCODE_5;
    /** 数字键6 */
    public static final int KEY_6 = KEYCODE_6;
    /** 数字键7 */
    public static final int KEY_7 = KEYCODE_7;
    /** 数字键8 */
    public static final int KEY_8 = KEYCODE_8;
    /** 数字键9 */
    public static final int KEY_9 = KEYCODE_9;
    
    /** 星号键 */
    public static final int KEY_XING = KEYCODE_STAR;
    /** #号键 */
    public static final int KEY_JING = KEYCODE_POUND;
    
    /** 菜单键 */
    public static final int MENU = KEYCODE_MENU;
    /** 小退键 */
    public static final int DEL = KEYCODE_DEL;
    /** OK键 */
    public static final int OK = KEYCODE_DPAD_CENTER;
    /** 扫描键 */
    public static final int SCAN = KEYCODE_BUTTON_MODE;
    /** 返回键 */
    public static final int BACK = KEYCODE_BACK;
    
    /** 上 */
    public static final int KEY_UP = KEYCODE_DPAD_UP;
    /** 下 */
    public static final int KEY_DOWN = KEYCODE_DPAD_DOWN;
    /** 左 */
    public static final int KEY_LEFT = KEYCODE_DPAD_LEFT;
    /** 右 */
    public static final int KEY_RIGHT = KEYCODE_DPAD_RIGHT;
    
    /**F1键*/
    public static final int KEY_F1 = KEYCODE_BUTTON_A;
    /**F2键*/
    public static final int KEY_F2 = KEYCODE_BUTTON_B;
    /**F3键*/
    public static final int KEY_F3 = KEYCODE_EXPLORER;
    /**F4键*/
    public static final int KEY_F4 = KEYCODE_SOFT_RIGHT;
    /**F5键*/
    public static final int KEY_F5 = KEYCODE_BUTTON_THUMBL;
    /**F6键*/
    public static final int KEY_F6 = KEYCODE_BUTTON_THUMBR;
    
    /** 音量+ */
    public static final int VOL_UP = KEYCODE_VOLUME_UP;
    /** 音量- */
    public static final int VOL_DOWN = KEYCODE_VOLUME_DOWN;
    
    /** 扫描左 */
    public static final int SCAN_LEFT = KEYCODE_BUTTON_L1;
    /** 扫描右 */
    public static final int SCAN_RIGHT = KEYCODE_BUTTON_R1;
    
}
