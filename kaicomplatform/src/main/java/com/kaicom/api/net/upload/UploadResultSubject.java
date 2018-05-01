package com.kaicom.api.net.upload;

import android.os.Handler;
import android.os.Looper;

import com.kaicom.api.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * 数据上传目标类<p>
 * 
 * 充当观察者目标<br>
 * 1) 主要用来通知观察者对象处理上传的结果<br>
 * 2) 通知观察者对象更改前台的未发数据和发送状态<br>
 * <br>
 * <b>注:</b>该对象已经将通知观察者的方法post到主线程中，外部无须再做处理
 * 
 * @author scj
 */
public class UploadResultSubject {

    private int unSendCount; // 未发数据数量
    private int unSendPicCount; // 未发图片数量
    private boolean isUploading; // 是否正在上传
    private List datas; // 上传成功数据集合
    // 观察者集合
    private List<UploadObserver> observers = new ArrayList<UploadObserver>();
    
    private Handler handler = new Handler(Looper.getMainLooper());
    private final Executor executor;

    private UploadResultSubject() {
        executor = new Executor() {
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }

    // 单例
    public static UploadResultSubject getInstance() {
        return UploadManagerHolder.instance;
    }

    private static class UploadManagerHolder {
        private static UploadResultSubject instance = new UploadResultSubject();
    }

    /**
     * 注册观察者
     * @param observer
     */
    public void attach(UploadObserver observer) {
        observers.add(observer);
    }

    /**
     * 移除观察者
     * @param observer
     */
    public void detach(UploadObserver observer) {
        observers.remove(observer);
    }

    /**
     * 通知观察者处理上传完成or失败等操作
     * @param bizType
     * @param message
     */
    public void notifyMessageChanged(final String bizType,
            final String message) {
        if (observers.size() == 0 || StringUtil.isEmpty(message))
            return;
        executor.execute(new Runnable() {

            @Override
            public void run() {
                for (UploadObserver each : observers) {
                    each.onMessage(bizType, message);
                }
            }
        });
    }

    /**
     * 通知观察者处理错误
     * @param message
     */
    public void notifyErrorOccurred(final String message) {
        if (observers.size() == 0 || StringUtil.isEmpty(message))
            return;
        executor.execute(new Runnable() {

            @Override
            public void run() {
                for (UploadObserver each : observers) {
                    each.onError(message);
                }
            }
        });

    }

    /**
     * 通知观察者对象更新界面未发数据
     */
    public void notifyUnSendCountChanged() {
        if (observers.size() == 0)
            return;
        
        executor.execute(new Runnable() {

            @Override
            public void run() {
                for (UploadObserver observer : observers) {
                    observer.onSendCountChanged(unSendCount, unSendPicCount);
                }
            }
        });

    }
    /**
     * 通知观察者对象更新界面数据
     */
    public void notifyUploadingSuccess(List list) {
        datas = list;
        if (observers.size() == 0)
            return;

        executor.execute(new Runnable() {

            @Override
            public void run() {
                for (UploadObserver observer : observers) {
                    observer.onUploadSuccess(datas);
                }
            }
        });

    }

    /**
     * 通知观察者对象更新界面的发送状态
     */
    public void notifyUploadStatusChanged() {
        if (observers.size() == 0)
            return;
        
        executor.execute(new Runnable() {
            
            @Override
            public void run() {
                for (UploadObserver observer : observers) {
                    observer.onUploadStatusChanged(isUploading);
                }
            }
        });
        
    }

    /**
     * 添加多条未发数据
     * <br>
     * 无须再调用notify方法
     * @param count
     */
    public void addUnSendCount(int count) {
        synchronized(this) {
            if (count > 0) {
                unSendCount += count;
                notifyUnSendCountChanged();
            }
        }
    }

    /**
     * 移除多条未发数据
     * <br>
     * 无须再调用notify方法
     * @param count
     */
    public void removeUnSendCount(int count) {
        synchronized (this) {
            if (count > 0) {
                unSendCount -= count;
                if (unSendCount < 0)
                    unSendCount = 0;
                notifyUnSendCountChanged();
            }
        }
    }

    /**
     * 添加多条未发图片数据
     * <br>
     * 无须再调用notify方法
     * @param count
     */
    public void addUnSendPicCount(int count) {
        synchronized (this) {
            if (count > 0) {
                unSendPicCount += count;
                notifyUnSendCountChanged();
            }
        }
    }

    /**
     * 移除多条未发图片数据
     * <br>
     * 无须再调用notify方法
     * @param count
     */
    public void removeUnSendPicCount(int count) {
        synchronized (this) {
            if (count > 0) {
                unSendPicCount -= count;
                if (unSendPicCount < 0)
                    unSendPicCount = 0;
                notifyUnSendCountChanged();
            }
        }
    }

    /**
     * 将上传状态变成正在上传中
     * <br>
     * 无须再调用notify方法
     */
    public synchronized void changeIntoUploadingStatus() {
        isUploading = true;
        notifyUploadStatusChanged();
    }

    /**
     * 将上传状态修改成不上传
     * <br>
     * 无须再调用notify方法
     */
    public synchronized void changeIntoUnUploadStatus() {
        isUploading = false;
        notifyUploadStatusChanged();
    }

    /**
     * 获取未发数据数量
     * @return
     */
    public synchronized int getUnSendCount() {
        return unSendCount;
    }

    /**
     * 设置未发数据数量
     * @param unSendCount 不能小于0
     */
    public void setUnSendCount(int unSendCount) {
        synchronized (this) {
            if (unSendCount > 0)
                this.unSendCount = unSendCount;
            else
                this.unSendCount = 0;
        }
        notifyUnSendCountChanged();
    }

    /**
     * 获取未发图片数量
     * @return
     */
    public synchronized int getUnSendPicCount() {
        return unSendPicCount;
    }

    /**
     * 设置未发图片数量
     * @param unSendPicCount 不能小于0
     */
    public void setUnSendPicCount(int unSendPicCount) {
        synchronized (this) {
            if (unSendPicCount > 0)
                this.unSendPicCount = unSendPicCount;
            else
                this.unSendPicCount = 0;
        }
        notifyUnSendCountChanged();
    }

    /**
     * 获取当前发送状态
     * @return
     */
    public synchronized boolean isUploading() {
        return isUploading;
    }

    /**
     * 设置当前发送状态
     * @param isUploading
     */
    public synchronized void setUploading(boolean isUploading) {
        this.isUploading = isUploading;
    }

}
