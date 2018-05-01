package com.kaicom.api.adapter;

import java.util.List;

import android.content.Context;

@Deprecated
public class ScanRecordAdapter<T> extends AbsCommonAdapter<T> {

    private int maxCount = 5; // 当前列表最多显示个数

    public ScanRecordAdapter(Context context, List<T> datas, int itemLayoutId) {
        super(context, datas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, T data, int position) {

    }

    @Override
    public int getCount() {
        int count = super.getCount();
        return count > maxCount ? maxCount : count;
    }

    /**
     * 获取总的记录数（包含不显示的记录）
     * @return
     */
    public int getTotalCount() {
        return super.getCount();
    }


}
