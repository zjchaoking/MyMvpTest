package com.kaicom.api.blue;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 蓝牙工具类
 * 
 * @author hb
 * 
 */
public class BluetoothUtil {

	// 注册搜寻蓝牙广播
	private IntentFilter intentFilter;

	// 蓝牙设备器
	private static BluetoothAdapter mAdapter;

	// 蓝牙工具类对象
	private static BluetoothUtil mBluetoothUtil;

	private BluetoothNameAddressListener mNameAddressListener;

	// 获取名称和地址不重复的集合
	Set<NameAddrEntity> bluetoothNameAddrSet = new LinkedHashSet<NameAddrEntity>();

	// 上下文对象
	private Context context;

	public BluetoothUtil(Context context) {
		this.context = context;
		getAdapter();
	}

	/**
	 * 获取蓝牙适配器
	 * 
	 * @return
	 */
	public static BluetoothAdapter getAdapter() {
		if (mAdapter == null)
			mAdapter = BluetoothAdapter.getDefaultAdapter();

		return mAdapter;
	}

	/**
	 * 打开蓝牙设备
	 */
	public void openBluetooth() {
		if (mAdapter != null)
			mAdapter.enable();
	}

	/**
	 * 关闭蓝牙设备
	 */
	public void closeBluetooth() {
		if (mAdapter != null)
			mAdapter.disable();
	}

	/**
	 * 蓝牙是否打开
	 */
	public boolean isEnabled() {
		if (mAdapter != null)
			return mAdapter.isEnabled();
		return false;
	}

	/**
	 * 获取已配对设备名称,地址
	 * 
	 * @return 设备名称,地址
	 */
	public Set<NameAddrEntity> getPairedDevice() throws Exception {
		// 获取名称和地址不重复的集合
		Set<NameAddrEntity> nameAddrPair = new LinkedHashSet<NameAddrEntity>();
		// 通过getBondedDevices方法来获取已经与本设备配对的设备
		Set<BluetoothDevice> device = mAdapter.getBondedDevices();
		if (device.size() > 0) {
			for (BluetoothDevice each : device) {
				NameAddrEntity mNameAddrEntity = new NameAddrEntity();
				mNameAddrEntity.setBluetoothName(each.getName());
				mNameAddrEntity.setBluetoothAddr(each.getAddress());
				nameAddrPair.add(mNameAddrEntity);
			}
		}

		return nameAddrPair;
	}

	/**
	 * 搜索周边未配对蓝牙设备
	 * 
	 * @param mNameAddressListener
	 * @throws Exception
	 */
	public void searchBluetoothDevice(
			BluetoothNameAddressListener mNameAddressListener) throws Exception {
		bluetoothNameAddrSet.clear();
		unregisterReceiver();
		// 设置广播信息过滤
		if (intentFilter == null) {
			this.mNameAddressListener = mNameAddressListener;
			intentFilter = new IntentFilter();
			intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
			intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
			intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
			intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
			// 注册广播接收器，接收并处理搜索结果
			context.registerReceiver(receiver, intentFilter);

		}
		// 取消已搜寻到的设备
		mAdapter.cancelDiscovery();
		// 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
		mAdapter.startDiscovery();
	}

	/**
	 * 获取搜寻的蓝牙设备的名称地址
	 */
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			try {
				if (BluetoothDevice.ACTION_FOUND.equals(action)) {
					BluetoothDevice device = intent
							.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

					if (device.getAddress() != null && device.getName() != null) {
						NameAddrEntity mNameAddrEntity = new NameAddrEntity();
						mNameAddrEntity.setBluetoothName(device.getName());
						mNameAddrEntity.setBluetoothAddr(device.getAddress());

						Set<NameAddrEntity> entity = getPairedDevice();
						Iterator<NameAddrEntity> iterator = entity.iterator();
						while (iterator.hasNext()) {
							NameAddrEntity addr = iterator.next();
							if (addr.getBluetoothAddr().equals(
									device.getAddress())) {
								return;
							}
						}

						bluetoothNameAddrSet.add(mNameAddrEntity);
						mNameAddressListener
								.getBluetoothNameAddress(bluetoothNameAddrSet);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 取消搜索蓝牙设备的广播
	 */
	public void unregisterReceiver() {
		if (intentFilter != null && receiver != null)
			context.unregisterReceiver(receiver);
		intentFilter = null;

	}

	/**
	 * 通过蓝牙地址获取蓝牙设备
	 * 
	 * @param strAddress
	 *            mac地址
	 * @return
	 */
	public BluetoothDevice getBluetoothDevice(String strAddress)
			throws Exception {
		return mAdapter.getRemoteDevice(strAddress);
	}

	/**
	 * 通过蓝牙地址和配对码配对蓝牙
	 * 
	 * @param strAddress
	 *            蓝牙地址
	 * @param strPin
	 *            蓝牙配对码
	 */
	public BluetoothDevice bluetoothPair(String strAddress, String strPin)
			throws Exception {

		BluetoothDevice device = getBluetoothDevice(strAddress);
		if (device.getBondState() != BluetoothDevice.BOND_BONDED) {// 判断给定地址下的device是否已经配对
			autoBond(device.getClass(), device, strPin);// 设置pin值
			createBond(device.getClass(), device);
		}

		return device;
	}

	// 自动配对设置Pin值
	private boolean autoBond(Class btClass, BluetoothDevice device,
			String strPin) throws Exception {
		@SuppressWarnings("unchecked")
		Method autoBondMethod = btClass.getMethod("setPin",
				new Class[] { byte[].class });
		Boolean result = (Boolean) autoBondMethod.invoke(device,
				new Object[] { strPin.getBytes() });
		return result;
	}

	// 开始配对
	private boolean createBond(Class btClass, BluetoothDevice device)
			throws Exception {
		Method createBondMethod = btClass.getMethod("createBond");
		Boolean returnValue = (Boolean) createBondMethod.invoke(device);
		return returnValue.booleanValue();
	}

	/**
	 * 获得标准的蓝牙地址
	 * 
	 * @param addr
	 *            蓝牙地址
	 * @return 返回一个标准的蓝牙地址,如果地址不正确返回null
	 */
	public String getStandardbluetoothAddr(String addr) {
		if (addr.matches("[0-9a-zA-Z]+") && addr.length() == 12) {
			String newAddr = "";
			for (int i = 0; i < 6; i++) {
				newAddr += addr.substring(i * 2, (i + 1) * 2);
				if (i < 5)
					newAddr = newAddr.concat(":");
			}
			addr = newAddr;

		}
		if (!checkBluetoothAddr(addr)) {
			return null;
		}

		return addr.toUpperCase();
	}

	/**
	 * 检查蓝牙地址是否符合格式
	 * 
	 * @param addr
	 *            蓝牙地址
	 * @return true 表示蓝牙地址正确 false 不正确
	 */
	public boolean checkBluetoothAddr(String addr) {
		if (addr != null)
			return addr
					.matches("[0-9a-zA-Z]{2}:[0-9a-zA-Z]{2}:[0-9a-zA-Z]{2}:[0-9a-zA-Z]{2}:"
							+ "[0-9a-zA-Z]{2}:[0-9a-zA-Z]{2}");
		return false;
	}

	/**
	 * 获取蓝牙未配对名称和地址接口
	 * 
	 * @author hb
	 * 
	 */
	public interface BluetoothNameAddressListener {
		public void getBluetoothNameAddress(Set<NameAddrEntity> nameAddrSet);
	}
}
