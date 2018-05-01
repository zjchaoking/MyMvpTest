package com.kaicom.api.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.kaicom.api.bluetooth.weight.BluetoothWeightParser;
import com.kaicom.api.bluetooth.weight.WeightParserFactory;
import com.kaicom.api.log.KlLoger;
import com.kaicom.api.view.toast.ToastTools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.kaicom.api.bluetooth.BluetoothState.CONNECTED;
import static com.kaicom.api.bluetooth.BluetoothState.CONNECTING;
import static com.kaicom.api.bluetooth.BluetoothState.CONNECT_FAIL;
import static com.kaicom.api.bluetooth.BluetoothState.NONE;

/**
 * 蓝牙服务
 *
 * @author scj
 */
public class BluetoothService implements Handler.Callback {

    private BluetoothAdapter mBtAdapter;
    private IntentFilter intentFilter;
    private Handler mHandler;
    private Context mContext;
    private WeightDecimalType mDecimalType;

    // 蓝牙观察者集合
    private List<BluetoothObserver> observers = new ArrayList<BluetoothObserver>();

    // 蓝牙搜索的监听事件
    private DiscoveryListener discoveryListener;
    // 蓝牙搜索到的设备集合
    private Set<BluetoothDeviceItem> bluetoothItemSet = new LinkedHashSet<BluetoothDeviceItem>();

    // 蓝牙连接线程
    private ConnectThread mConnectThread;
    // 数据读取线程
    private CommunicationThread mCommunicationThread;

    private volatile boolean isConnectting; // 正在连接
    private boolean isStart;
    private String mCurAddress; // 当前正在连接的地址

    public BluetoothService(Context context) {
        mContext = context;
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        mHandler = new Handler(Looper.getMainLooper(), this);
    }

    private static boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public void startService() {
        System.out.println("startService");
        isStart = true;
        mDecimalType = BluetoothPrefences.getInstance(mContext)
                .getWeightDecimalType();
        setState(NONE);

        intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        mContext.registerReceiver(bluetoothReceiver, intentFilter);
    }

    void setDiscoveryListener(DiscoveryListener listener) {
        this.discoveryListener = listener;
    }

    void setDecimalType(WeightDecimalType decimalType) {
        BluetoothPrefences.getInstance(mContext).setDecimalType(decimalType);
        mDecimalType = decimalType;
    }

    /**
     * 设置当前蓝牙的状态
     *
     * @param state
     * @hide
     */
    public synchronized void setState(BluetoothState state) {
        Message msg = mHandler.obtainMessage(STATE_CHANGED);
        msg.obj = state;
        msg.sendToTarget();
    }

    /**
     * 搜索蓝牙设备
     *
     * @hide
     */
    public void searchBluetoothDevice() {
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
            mBtAdapter.startDiscovery();
        }
    }

    public static final int PAIRING_VARIANT_PASSKEY = 1;

    /**
     * 蓝牙广播
     */
    private BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                handleSearchedDevices(device);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                if (discoveryListener != null)
                    discoveryListener.onStartDiscovery();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                    .equals(action)) {
                if (discoveryListener != null) {
                    discoveryListener
                            .onFinishDiscovery(bluetoothItemSet.size());
                }
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                ToastTools.showToast("蓝牙配对状态改变");
            }
        }
    };

    // 处理搜索到的蓝牙设备
    private void handleSearchedDevices(BluetoothDevice device) {
        if (BluetoothDevice.BOND_BONDED == device.getBondState()) {
            return;
        }
        if (TextUtils.isEmpty(device.getAddress())) {
            return;
        }

        BluetoothDeviceItem item = new BluetoothDeviceItem();
        item.setName(device.getName());
        item.setAddress(device.getAddress());
        bluetoothItemSet.add(item);

        if (discoveryListener != null)
            discoveryListener.onFoundUnpairedDevices(bluetoothItemSet);
    }

    /**
     * 自动配对
     *
     * @param btDevice
     * @param passKey
     * @throws BluetoothException
     */
    public void autoPair(BluetoothDevice btDevice, int passKey)
            throws BluetoothException {
        try {
            BluetoothUtils.setPassKey(btDevice, passKey);
            BluetoothUtils.createBond(btDevice);
            BluetoothUtils.cancelBondProcess(btDevice);
        } catch (Exception e) {
            throw new BluetoothException(e);
        }
    }

    /**
     * 自动配对
     *
     * @param btDevice
     * @throws BluetoothException
     */
    public void autoPair(BluetoothDevice btDevice, String pin)
            throws BluetoothException {
        try {
            BluetoothUtils.setPin(btDevice, pin);
            BluetoothUtils.createBond(btDevice);
            BluetoothUtils.cancelBondProcess(btDevice);
        } catch (Exception e) {
            throw new BluetoothException(e);
        }
    }

    public synchronized void connect(final BluetoothDevice device) {
        boolean isConnected = (mCommunicationThread != null && !mCommunicationThread.isQuit());
        setState(CONNECTING);
        if (isConnected && device.getAddress().equals(mCurAddress)) {
            setState(CONNECTED);
            isConnectting = false;
            return;
        }

        // 正在连接的状态
        if (isConnectting)
            return;
        isConnectting = true;

        cancelCommunicationThread();
        cancelConnectThread();

        setState(CONNECTING);

        long time = 100;
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                mConnectThread = new ConnectThread(BluetoothService.this,
                        device);
                mConnectThread.start();
            }
        }, time);

    }

    private void sleep(int milliSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliSeconds);
        } catch (InterruptedException e) {
        }
    }

    synchronized void alertToast(final String message) {
        if (isInMainThread())
            ToastTools.showToast(message);
        else
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    ToastTools.showToast(message);
                }
            });
    }

    // 连接成功
    synchronized void connectSuccess(BluetoothSocket btSocket,
                                     BluetoothDevice btDevice) {
        isConnectting = false;
        setState(CONNECTED);

        mCurAddress = btDevice.getAddress();
        cancelCommunicationThread();

        BluetoothPrefences prefences = BluetoothPrefences.getInstance(mContext);

        // 保存蓝牙地址
        String lastAddress = prefences.getLastDeviceAddress();
        String address = btDevice.getAddress();
        if (!address.equals(lastAddress)) {
            prefences.setLastDeviceAddress(address);
        }

        // 设置秤类型
        ScalesType type = prefences.getScalesType(btDevice);
        BluetoothWeightParser weightParser = WeightParserFactory
                .createWeightHandler(type);

        if (weightParser == null) {
            setState(BluetoothState.UNKNOW_SCALES);
        }

        mCommunicationThread = new CommunicationThread(this, btSocket);
        mCommunicationThread.setWeightParser(weightParser);
        mCommunicationThread.start();
    }

    // 蓝牙连接失败
    synchronized void connectFail() {
        isConnectting = false;
        setState(CONNECT_FAIL);

        cancelCommunicationThread();
        cancelConnectThread();
        alertToast("蓝牙连接失败");
    }

    private void cancelConnectThread() {
        if (mConnectThread != null) {
            if (mConnectThread.isConnectting()) {
                BluetoothUtils.disableBluetooth();
                sleep(200);
                BluetoothUtils.enableBluetooth();
            }
            closeConnectThread();
        }
    }

    private void closeConnectThread() {
        try {
            isConnectting = false;
            mConnectThread.cancel();
            mConnectThread.interrupt();
            mConnectThread = null;
        } catch (Exception e) {
            KlLoger.error("closeConnectThread", e);
        }
    }

    private synchronized void cancelCommunicationThread() {
        if (mCommunicationThread != null) {
            mCommunicationThread.cancel();
            mCommunicationThread = null;
        }
    }

    synchronized void postReceiveData(byte[] data) {
        postReceiveStringData(new String(data));
    }

    synchronized void postReceiveStringData(String data) {
        if (data == null)
            return;
        Message msg = mHandler.obtainMessage(MESSAGE_READ);
        msg.obj = data;
        msg.sendToTarget();
    }

    public void register(BluetoothObserver observer) {
        observers.add(observer);
    }

    public void unregister(BluetoothObserver observer) {
        observers.remove(observer);
    }

    public void notifyDataChanged(String data) {
        String weight = convertDecimalData(data);
        for (BluetoothObserver observer : observers) {
            if (observer instanceof BluetoothWeightObserver) {
                ((BluetoothWeightObserver) observer)
                        .onResolveWeightData(weight);
            }
        }
    }

    private String convertDecimalData(String data) {
        String decimalFormat = "#0.00";
        switch (mDecimalType) {
            case NO_KEEP:
                return data;
            case KEEP_ONE:
                decimalFormat = "#0.0";
                break;
            case KEEP_TWO:
                decimalFormat = "#0.00";
                break;
            case KEEP_THREE:
                decimalFormat = "#0.000";
                break;
            default:
                break;
        }
        if (!BluetoothWeightParser.isNumberDecimals(data)) {
            data = "0";
        }
        double num = Double.parseDouble(data);
        DecimalFormat df = new DecimalFormat(decimalFormat);
        return df.format(num) + "";
    }
    
    public void setScalesType(String macAddress, ScalesType type) {
    	synchronized (this) {
    		if (mCommunicationThread != null && !mCommunicationThread.isQuit()) {
    			BluetoothWeightParser weightParser = WeightParserFactory
    					.createWeightHandler(type);
    			mCommunicationThread.setWeightParser(weightParser);
    		}
		}
    }

    public void notifyStateChanged(BluetoothState state) {
        for (BluetoothObserver observer : observers) {
            observer.onStateChanged(state);
        }
    }

    private boolean hasObserver() {
        return observers.size() > 0;
    }

    public static final int STATE_CHANGED = 0x33;
    public static final int MESSAGE_READ = 0x34;
    public static final int CLOSE_BLUETOOTH_CONNECT = 35;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MESSAGE_READ:
                if (hasObserver())
                    notifyDataChanged(msg.obj.toString());
                break;
            case STATE_CHANGED:
                if (hasObserver())
                    notifyStateChanged((BluetoothState) msg.obj);
                break;
            case CLOSE_BLUETOOTH_CONNECT:
                if (mConnectThread != null) {
                    closeConnectThread();
                }
                break;
            default:
                break;
        }
        return true;
    }

    public void stopService() {
        System.out.println("stopService");
        cancelCommunicationThread();
        cancelConnectThread();
        mContext.unregisterReceiver(bluetoothReceiver);
        mCurAddress = null;
        isStart = false;
    }

    public boolean isRunning() {
        return isStart;
    }

}
