package com.kaicom.api.broad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by cjb on 2016/4/12 0012.
 */
public class MachineCodeBroadcast extends BroadcastReceiver {

    public static final String GET_MACHINE_CODE_ACTION = "com.android.receive_pda_sn";
    public static final String REQUEST_MACHINE_CODE_ACTION = "com.android.receive_get_pda_sn";
    static MachineCodeListener mMachineCodeListener;

    public MachineCodeBroadcast(){

    }

    public MachineCodeBroadcast(MachineCodeListener machineCodeListener) {
        mMachineCodeListener = machineCodeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.android.receive_pda_sn")) {
            String data = intent.getStringExtra("pda_sn");
            if(mMachineCodeListener!=null){
                mMachineCodeListener.onMachineCodeListener(data);
            }
        }
    }

    public interface MachineCodeListener {
        void onMachineCodeListener(String code);
    }
}
