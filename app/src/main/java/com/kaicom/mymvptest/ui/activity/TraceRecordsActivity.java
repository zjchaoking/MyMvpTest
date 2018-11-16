package com.kaicom.mymvptest.ui.activity;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaicom.api.util.DateUtil;
import com.kaicom.api.util.FileUtil;
import com.kaicom.api.view.dialog.DialogTools;
import com.kaicom.mymvptest.R;
import com.kaicom.mymvptest.ui.adapters.AbsRecyclerViewAdapter;
import com.kaicom.mymvptest.ui.adapters.TranceRecordsRecyclerViewAdapter;
import com.kaicom.mymvptest.base.BaseActivity;
import com.kaicom.mymvptest.datasource.TraceRecordsOper;
import com.kaicom.mymvptest.datasource.entity.TraceRecordsEntity;

import java.util.List;

import butterknife.BindView;

import static com.kaicom.mymvptest.files.AppConfigs.RECORD_INTERVAL_TIME;
import static com.kaicom.mymvptest.files.AppConfigs.RECORD_PATH;

/**
 * 我的行程
 */
public class TraceRecordsActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_text)
    TextView tvRightText;
    @BindView(R.id.rv_menu)
    RecyclerView rvMenu;
    private TranceRecordsRecyclerViewAdapter viewAdapter;
    private List<TraceRecordsEntity> itemEntities;

    @Override
    protected int getViewId() {
        return R.layout.activity_trace_records;
    }

    @Override
    protected void init() {
        setTitle("我的行程");
        itemEntities = traceRecordsOper.getAllDescByTime();
        rvMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMenu.setHasFixedSize(true);
        rvMenu.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = 3;
                outRect.top = 3;
                outRect.right = 3;
                outRect.bottom = 3;
            }
        });
        viewAdapter = new TranceRecordsRecyclerViewAdapter(itemEntities, this);
        rvMenu.setAdapter(viewAdapter);
        viewAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TraceRecordsEntity entity = viewAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString(RECORD_PATH, entity.getFilePath());
                long intervalMins = DateUtil.getIntervalMins(entity.getStartTime(), entity.getEndTime());
                bundle.putLong(RECORD_INTERVAL_TIME, intervalMins);
                toNextActivityWithBundle(TraceDetailActivity.class, bundle);
            }
        });
        viewAdapter.setOnItemLongClickListener(new AbsRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                DialogTools.showDialog(TraceRecordsActivity.this, "是否删除该条记录？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TraceRecordsEntity entity = viewAdapter.getItem(position);
                        new TraceRecordsOper().deleteOneData(entity);
                        viewAdapter.remove(position);
                        viewAdapter.notifyDataSetChanged();
                        FileUtil.deleteFile(entity.getFilePath());
                    }
                });
            }
        });
    }

}
