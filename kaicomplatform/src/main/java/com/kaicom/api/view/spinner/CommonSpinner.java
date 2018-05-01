package com.kaicom.api.view.spinner;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * <h4>抽象下拉菜单</h4>
 * 客户程序一般需要重写getSpinnerItemRes和getSpinnerDropDownRes方法，
 * 传入自定义的布局样式。
 * 
 * @author scj
 * 
 */
public abstract class CommonSpinner<T> extends Spinner {

    protected Context mContext = null;

    public CommonSpinner(Context paramContext) {
        super(paramContext);
        mContext = paramContext;
        initAdapter(paramContext);
    }

    public CommonSpinner(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initAdapter(paramContext);
    }

    public CommonSpinner(Context paramContext, AttributeSet paramAttributeSet,
            int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        initAdapter(paramContext);
    }

    /**
     * 初始化adapter
     * @param paramContext
     */
    protected void initAdapter(Context paramContext) {
        ArrayAdapter<T> localArrayAdapter = new ArrayAdapter<T>(getContext(),
                getSpinnerItemRes(), loadData());
        localArrayAdapter.setDropDownViewResource(getSpinnerDropDownRes());
        setAdapter(localArrayAdapter);
    }

    /**
     * spinner的布局
     * <br>
     * 默认:android.R.layout.simple_spinner_item
     * <br>
     * <h6>注意：</h6>
     * 自定义布局时id必须为@android:id/text1
     * @return res
     */
    protected int getSpinnerItemRes() {
        return android.R.layout.simple_spinner_item;
    }

    /**
     * spinner的下拉布局
     * <br>
     * 默认:android.R.layout.simple_spinner_dropdown_item
     * <br>
     * <h6>注意：</h6>
     * 自定义布局时id必须为@android:id/text1
     * @return res
     */
    protected int getSpinnerDropDownRes() {
        return android.R.layout.simple_spinner_dropdown_item;
    }

    /**
     * 加载需要在spinner上显示的数据
     * @param paramContext
     * @return List
     */
    protected abstract List<T> loadData();

    /**
     * 获取选中菜单的选项
     * @return T
     */
    public abstract T getSelectedData();

}