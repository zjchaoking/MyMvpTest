package com.kaicom.api.view.apapter_view;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.kaicom.api.adapter.ScanRecordAdapter;

/**
 * 扫描记录列表
 * @author scj
 *
 */
public class ScanRecordListView<T> extends ListView {

    private ScanRecordAdapter<T> mAdapter;

    public ScanRecordListView(Context context) {
        this(context, null);
    }

    public ScanRecordListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanRecordListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void bindData() {
        mAdapter = new ScanRecordAdapter<T>(getContext(), new ArrayList<T>(), 0);
        setAdapter(mAdapter);
    }


}
