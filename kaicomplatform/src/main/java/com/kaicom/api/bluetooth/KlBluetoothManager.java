package com.kaicom.api.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.kaicom.api.bluetooth.BluetoothUtils.getBluetoothDevice;

/**
 * <h4>蓝牙管理类</h4>
 * <p/>
 * 功能: 蓝牙开关、搜索、配对、连接操作
 * 注: 在主线程中调用
 *
 * @author scj
 */
public class KlBluetoothManager {

    private static KlBluetoothManager bluetoothManager;

    private BluetoothAdapter mBtAdapter;
    private BluetoothService mService;
    private Context mContext;

    private KlBluetoothManager(Context context) {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        mContext = context;
        mService = new BluetoothService(mContext);
    }

    public static synchronized KlBluetoothManager getInstance(Context context) {
        if (bluetoothManager == null) {
            bluetoothManager = new KlBluetoothManager(
                    context.getApplicationContext());
        }
        bluetoothManager.startBluetoothService();
        return bluetoothManager;
    }


    /**
     * 判断蓝牙设备是否打开
     *
     */
    public static boolean isBluetoothEnabled() {
        return BluetoothUtils.isBluetoothEnabled();
    }

    /**
     * 打开或关闭蓝牙设备
     *
     * @param enable
     */
    public void setBluetoothEnabled(boolean enable) {
        if (enable)
            enable();
        else
            disable();
    }

    /**
     * 打开蓝牙设备
     */
    public void enable() {
        BluetoothUtils.enableBluetooth();
    }

    /**
     * 关闭蓝牙设备
     */
    public void disable() {
        BluetoothUtils.disableBluetooth();
    }


    /**
     * 设置蓝牙搜索监听事件
     *
     * @param listener
     */
    public void setDiscoveryListener(DiscoveryListener listener) {
        mService.setDiscoveryListener(listener);
    }

    /**
     * 搜索蓝牙设备
     */
    public void searchDevices() {
        mService.searchBluetoothDevice();
    }


    /**
     * 获得已配对的蓝牙设备集合
     *
     * @return
     */
    public LinkedHashSet<BluetoothDeviceItem> getPairedDevices() {
        LinkedHashSet<BluetoothDeviceItem> itemSet = new LinkedHashSet<BluetoothDeviceItem>();
        Set<BluetoothDevice> devices = mBtAdapter.getBondedDevices();

        if (devices != null) {
            for (BluetoothDevice bluetoothDevice : devices) {
                BluetoothDeviceItem item = new BluetoothDeviceItem();
                item.setName(bluetoothDevice.getName());
                item.setAddress(bluetoothDevice.getAddress());
                itemSet.add(item);
            }
        }
        return itemSet;
    }


    /**
     * 蓝牙配对
     *
     * @param address
     * @param pinCode
     * @return
     * @throws Exception 地址不对时抛出IllegalArgumentException, 其他异常为反射问题
     */
    public BluetoothDevice pair(String address, String pinCode)
            throws Exception {
        BluetoothDevice device = BluetoothUtils.getBluetoothDevice(address);
        if (device.getBondState() == BluetoothDevice.BOND_NONE) {
            BluetoothUtils.createBond(device);
        }
        return device;
    }

    /**
     * 连接最近连接过的蓝牙设备
     *
     * @throws BluetoothException
     */
    public void connect() throws BluetoothException {
        String address = BluetoothPrefences.getInstance(mContext).getLastDeviceAddress();
        if (TextUtils.isEmpty(address)) {
            throw new BluetoothException("之前未连接过蓝牙，请重新选择设备");
        }
        connect(address);
    }

    /**
     * 连接蓝牙设备
     *
     * @param address
     * @throws BluetoothException
     */
    public void connect(String address) throws BluetoothException {
        try {
            connect(getBluetoothDevice(address));
        } catch (Exception e) {
            throw new BluetoothException(e);
        }
    }

    /**
     * 连接蓝牙设备
     *
     * @param device
     * @throws BluetoothException
     */
    public void connect(BluetoothDevice device) throws BluetoothException {
        if (device == null)
            throw new BluetoothException("BluetoothDevice is null");
        mService.connect(device);
    }


    private void startBluetoothService() {
        if (mService != null && !mService.isRunning())
            mService.startService();
    }

    public void disconnect() {
        if (mService != null && mService.isRunning())
            mService.stopService();
    }


    /**
     * 注册蓝牙观察者对象
     *
     * @param observer
     */
    public void register(BluetoothObserver observer) {
        mService.register(observer);
    }

    /**
     * 取消蓝牙观察者
     *
     * @param observer
     */
    public void unregister(BluetoothObserver observer) {
        mService.unregister(observer);
    }

    /**
     * 设置蓝牙秤类型
     *
     * @param macAddress
     * @param type
     */
    public void setScalesType(String macAddress, ScalesType type) {
        if (BluetoothUtils.isBluetoothAddress(macAddress)) {
            BluetoothPrefences.getInstance(mContext).setScalesType(macAddress, type);
            if (mService.isRunning()) {
            	mService.setScalesType(macAddress, type);
            }
        }
    }

    /**
     * 设置蓝牙称重数据小数位
     *
     * @param type
     */
    public void setDecimalType(WeightDecimalType type) {
        mService.setDecimalType(type);
    }

}
