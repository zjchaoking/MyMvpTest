package com.kaicom.mymvptest.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.kaicom.api.util.DateUtil;
import com.kaicom.mymvptest.R;
import com.kaicom.mymvptest.datasource.entity.TraceRecordsEntity;

import java.util.List;

/**
 */
public class TranceRecordsRecyclerViewAdapter
        extends AbsRecyclerViewAdapter<TraceRecordsEntity> {

    private Context context;

    public TranceRecordsRecyclerViewAdapter(@NonNull List<TraceRecordsEntity> items, Context context) {
        super(items, R.layout.menu_cardview);
        this.context = context;
    }

    @Override
    protected void convert(RecyclerViewHolder holder, TraceRecordsEntity item, final int position) {
        ((TextView) holder.getView(R.id.tv_name)).setText(item.getStartTime());
        long intervalMins = DateUtil.getIntervalMins(item.getStartTime(), item.getEndTime());
        ((TextView) holder.getView(R.id.tv_time)).setText(intervalMins+"分钟");

    }
}
