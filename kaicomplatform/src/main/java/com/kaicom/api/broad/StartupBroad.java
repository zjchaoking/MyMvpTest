package com.kaicom.api.broad;

import com.kaicom.api.MainActivity;
import com.kaicom.api.preference.PlatformPreference;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 自启动广播
 * 
 * @author scj
 */
public class StartupBroad extends BroadcastReceiver {

    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_BOOT)) {
            boolean isFirst = PlatformPreference.getInstance()
                    .getFirstLaunch();
            if (!isFirst) {
                Intent startIntent = new Intent(context, MainActivity.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(startIntent);
            }
        }

    }

}
