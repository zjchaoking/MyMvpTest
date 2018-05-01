package com.kaicom.api.view.text;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 无焦点跑马灯TextView
 * 
 * @author scj
 *
 */
public class MarqueeTextView extends TextView {

    public MarqueeTextView(Context appCon) {  
        this(appCon, null); 
    }  
  
    public MarqueeTextView(Context context, AttributeSet attrs) {  
        this(context, attrs, 0);  
    }  
  
    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
        init();
    }
    
    public void init() {
        setEllipsize(TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
        setSingleLine();
    }
  
    @Override  
    public boolean isFocused() {
        return true;  
    }  
  
    @Override  
    protected void onFocusChanged(boolean focused, int direction,  
            Rect previouslyFocusedRect) {  
    }  
    
}
