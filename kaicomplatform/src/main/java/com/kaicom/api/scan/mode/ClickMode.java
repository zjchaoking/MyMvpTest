package com.kaicom.api.scan.mode;

import static com.kaicom.api.scan.KeyCodeConstant.DEL;
import static com.kaicom.api.scan.KeyCodeConstant.SCAN;
import static com.kaicom.api.scan.KeyCodeConstant.SCAN_LEFT;
import static com.kaicom.api.scan.KeyCodeConstant.SCAN_RIGHT;
import static com.kaicom.api.scan.KeyCodeConstant.VOL_UP;
import static com.kaicom.api.scan.KeyCodeConstant.VOL_DOWN;

import com.kaicom.api.manager.ModelRecognizerTools;
import com.kaicom.api.scan.ScanManager;

import android.view.KeyEvent;

/**
 * 单按键触发模式
 * 
 * @author scj
 */
public class ClickMode implements ScanTrigMode {

    @Override
    public boolean event(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            switch (keyCode) {
            case SCAN:
            case SCAN_LEFT:
            case SCAN_RIGHT:
                ScanManager.getInstance().startScan();
                break;
            case VOL_UP:
            case VOL_DOWN:
            case KeyEvent.KEYCODE_CAMERA:
            	if (!ModelRecognizerTools.isBarcodeScanner()) {
            		ScanManager.getInstance().startCamera();
            		return true;
            	}
            	break;
            case DEL:
                ScanManager.getInstance().endScan();
                break;
            default:
                break;
            }
        }
        return false;
    }
    
}
