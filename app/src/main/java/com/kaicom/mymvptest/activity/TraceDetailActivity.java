package com.kaicom.mymvptest.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.kaicom.mymvptest.R;
import com.kaicom.mymvptest.amap.PathSmoothTool;
import com.kaicom.mymvptest.amap.SensorEventHelper;
import com.kaicom.mymvptest.amap.TraceAsset;
import com.kaicom.mymvptest.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.kaicom.api.util.StringUtil.getFloatWithTwo;
import static com.kaicom.mymvptest.files.AppConfigs.RECORD_INTERVAL_TIME;
import static com.kaicom.mymvptest.files.AppConfigs.RECORD_PATH;

/**
 * 展示行程轨迹详情页面
 */
public class TraceDetailActivity extends BaseActivity implements AMap.OnMapLoadedListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_text)
    TextView tvRightText;
    @BindView(R.id.detail_map)
    MapView detailMap;
    @BindView(R.id.record_show_activity_origin_button)
    CheckBox recordShowActivityOriginButton;
    @BindView(R.id.record_show_activity_kalman_button)
    CheckBox recordShowActivityKalmanButton;
    @BindView(R.id.record_show_activity_trace_group)
    LinearLayout recordShowActivityTraceGroup;
    @BindView(R.id.location_errInfo_text)
    TextView locationErrInfoText;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_paifang)
    TextView tvPaifang;
    @BindView(R.id.tv_kaluli)
    TextView tvKaluli;
    private MapView mMapView = null;
    private AMap amap = null;
    private List<LatLng> mOriginList = new ArrayList<LatLng>();
    private Polyline mOriginPolyline, mkalmanPolyline;
    private CheckBox mOriginbtn, mkalmanbtn;
    private PathSmoothTool mpathSmoothTool;
    private TextView mLocationErrText;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private boolean mFirstFix = false;
    private Marker mLocMarker;
    private SensorEventHelper mSensorHelper;
    private Circle mCircle;
    public static final String LOCATION_MARKER_FLAG = "mylocation";
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private String filePath = "";//轨迹文件路径
    private long intervalTime;//轨迹文件记录时长

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            filePath = getIntent().getStringExtra(RECORD_PATH);
            intervalTime = getIntent().getLongExtra(RECORD_INTERVAL_TIME, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvTime.setText(intervalTime + "");
        mMapView = (MapView) findViewById(R.id.detail_map);
        mMapView.onCreate(savedInstanceState);
        initMap();
        mpathSmoothTool = new PathSmoothTool();
        mpathSmoothTool.setIntensity(4);
        addLocpath();
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_trace_detail;
    }

    @Override
    protected void init() {
        setTitle("行程详情");
        setRightText("分享");
    }

    private void initMap() {
        if (amap == null) {
            amap = mMapView.getMap();
        }
        amap.setOnMapLoadedListener(this);
        mOriginbtn = (CheckBox) findViewById(R.id.record_show_activity_origin_button);
        mkalmanbtn = (CheckBox) findViewById(R.id.record_show_activity_kalman_button);
        mOriginbtn.setOnCheckedChangeListener(this);
        mkalmanbtn.setOnCheckedChangeListener(this);
    }

    //在地图上添加本地轨迹数据，并处理
    private void addLocpath() {
        mOriginList = TraceAsset.parseLocationsData(filePath);
        tvDistance.setText(getDistance(mOriginList) + "");
        if (mOriginList != null && mOriginList.size() > 0) {
            mOriginPolyline = amap.addPolyline(new PolylineOptions().addAll(mOriginList).color(Color.GREEN));
            amap.moveCamera(CameraUpdateFactory.newLatLngBounds(getBounds(mOriginList), 200));
        }
        pathOptimize(mOriginList);
        mkalmanbtn.setChecked(false);
    }

    private float getDistance(List<LatLng> mOriginList) {
        float distance = 0;
        for (int i = 0; i < mOriginList.size(); i++) {
            if (i != 0) {
                distance += AMapUtils.calculateLineDistance(mOriginList.get(i), mOriginList.get(i - 1));
            }
        }
        return getFloatWithTwo(distance / 1000);//获得km数,保留2位小数
    }

    //轨迹平滑优化
    public List<LatLng> pathOptimize(List<LatLng> originlist) {
        List<LatLng> pathoptimizeList = mpathSmoothTool.pathOptimize(originlist);
        mkalmanPolyline = amap.addPolyline(new PolylineOptions().addAll(pathoptimizeList).color(Color.parseColor("#FFC125")));
        return pathoptimizeList;
    }

    @Override
    public void onMapLoaded() {
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int id = compoundButton.getId();
        switch (id) {
            case R.id.record_show_activity_origin_button:
                if (mOriginPolyline != null) {
                    mOriginPolyline.setVisible(b);
                }
                break;
            case R.id.record_show_activity_kalman_button:
                if (mkalmanPolyline != null) {
                    mkalmanPolyline.setVisible(b);
                }
                break;
        }
    }

    private LatLngBounds getBounds(List<LatLng> pointlist) {
        LatLngBounds.Builder b = LatLngBounds.builder();
        if (pointlist == null) {
            return b.build();
        }
        for (int i = 0; i < pointlist.size(); i++) {
            b.include(pointlist.get(i));
        }
        return b.build();

    }

}
