package com.kaicom.api.net.upload;


import java.util.List;

/**
 * 上传观察者<p>
 * 
 * 处理发送成功or失败or错误及未发数据管理
 * @author scj
 */
public interface UploadObserver {

    /**
     * 处理上传完成、出错等消息
     * @param bizType
     * @param message
     */
    public void onMessage(String bizType, String message);
    
    /**
     * 处理上传出错消息
     * @param message 错误信息
     */
    public void onError(String message);
    
    /**
     * 未发数据发生变化, 用于更新界面的未发数
     * @param unSendCount
     * @param unSendPicCount
     */
    public void onSendCountChanged(int unSendCount, int unSendPicCount);
            
    /**
     * 上传状态发生变化, 用于更新界面上的发送状态
     * @param isUploading 是否正在上传
     */
    public void onUploadStatusChanged(boolean isUploading);
    /**
     * 上传成功后，传递上传成功的数据集合
     * @param datas 上传成功的数据集合
     */
    public void onUploadSuccess(List datas);

}
