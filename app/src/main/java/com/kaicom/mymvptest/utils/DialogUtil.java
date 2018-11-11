package com.kaicom.mymvptest.utils;

import android.content.Context;

import com.kaicom.mymvptest.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by jc on 2018/11/11.
 */

public class DialogUtil {
    private static  SweetAlertDialog loadingDialog;
    public static  Context context;

    public static void showSingleDialog(String errorMsg){
        SweetAlertDialog dialog = new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText("提示");
        dialog.setContentText(errorMsg);
        dialog.setConfirmText("确认");
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        dialog.show();
    }
    public static void showErrorDialog(String errorMsg, boolean isShowCancelButton, final SweetAlertDialog.OnSweetClickListener listener){
        SweetAlertDialog dialog = new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText("提示");
        dialog.setContentText(errorMsg);
        dialog.setConfirmText("确认");
        if(isShowCancelButton){
            dialog.setCancelText("取消");
        }
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                listener.onClick(sweetAlertDialog);
            }
        });
        dialog.show();
    }
    public static void showNormalDialog(String msg){
        SweetAlertDialog dialog = new SweetAlertDialog(context);
        dialog.setTitleText("提示");
        dialog.setContentText(msg);
        dialog.show();
    }
    public static void showLoadingDialog(String msg,boolean isCancelable){
        loadingDialog = new SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE);
        loadingDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
        loadingDialog.setTitleText(msg);
        loadingDialog.setCancelable(isCancelable);
        loadingDialog.show();
    }
    public static void showWarnDialog(String errorMsg, boolean isShowCancelButton, final SweetAlertDialog.OnSweetClickListener listener){
        SweetAlertDialog dialog = new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText("警告");
        dialog.setContentText(errorMsg);
        dialog.setConfirmText("确认");
        if(isShowCancelButton){
            dialog.setCancelText("取消");
        }
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                listener.onClick(sweetAlertDialog);
            }
        });
        dialog.show();
    }

    public static void dismissLoadingDialog(){
        if(loadingDialog!=null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

}
