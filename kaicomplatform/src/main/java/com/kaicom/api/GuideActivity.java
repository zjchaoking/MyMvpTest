package com.kaicom.api;

import static com.kaicom.api.KaicomApplication.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.kaicom.api.adapter.GuidePagerAdapter;
import com.kaicom.fw.R;

/**
 * 引导界面
 * @author scj
 *
 */
public class GuideActivity extends Activity implements OnClickListener,
		OnPageChangeListener {

	private ViewPager viewPager;
	private GuidePagerAdapter vpAdapter;
	private List<View> views;
	// 引导图片资源
	private int[] pics = null;
	// 底部小点的图片
	private ImageView[] points;
	// 记录当前选中位置
	private int currentIndex;
	// 包裹小圆点的布局
	private LinearLayout pointGroup;
	private Button startBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_layout);
		initView();
		bindData();
	}

	private void initView() {
		views = new ArrayList<View>();
		viewPager = (ViewPager) findViewById(R.id.view_pages);
		vpAdapter = new GuidePagerAdapter(views);
		pointGroup = (LinearLayout) findViewById(R.id.point_group);
		startBtn = (Button) findViewById(R.id.btn_start);
		startBtn.setNextFocusLeftId(R.id.view_pages);
		startBtn.setNextFocusRightId(R.id.view_pages);
		startBtn.setNextFocusDownId(R.id.view_pages);
		startBtn.setNextFocusUpId(R.id.view_pages);
		startBtn.setOnClickListener(this);
		viewPager.requestFocus();
	}

	private void bindData() {
		// 定义一个布局并设置参数
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);

		pics = KaicomApplication.app.getGuideImgs();

		// 初始化引导图片列表
		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(params);
			iv.setImageResource(pics[i]);
			iv.setScaleType(ScaleType.FIT_XY);
			views.add(iv);
		}
		viewPager.setAdapter(vpAdapter);
		viewPager.setOnPageChangeListener(this);

		// 初始化底部小点
		initPoint();
	}

	/**
	 * 初始化底部小点
	 */
	private void initPoint() {
		points = new ImageView[pics.length];
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 0, 5, 0);

		// 循环取得小点图片
		for (int i = 0; i < pics.length; i++) {
			points[i] = new ImageView(this);
			points[i].setLayoutParams(params);
			points[i].setBackgroundResource(R.drawable.point_img);
			points[i].setEnabled(false);
			points[i].setOnClickListener(this);
			points[i].setTag(i);
			pointGroup.addView(points[i]);
		}

		// 设置当面默认的位置
		currentIndex = 0;
		// 设置为白色，即选中状态
		points[currentIndex].setEnabled(true);
	}

	/**
	 * 当滑动状态改变时调用
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	/**
	 * 当前页面被滑动时调用
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	/**
	 * 当新的页面被选中时调用
	 */
	@Override
	public void onPageSelected(int position) {
		// 设置底部小点选中状态
		setCurDot(position);
		if (position == (points.length - 1)) {
			startBtn.setVisibility(View.VISIBLE);
			startBtn.requestFocus();
		} else {
			startBtn.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置当前的小点的位置
	 */
	private void setCurDot(int positon) {
		// 排除异常情况
		if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
			return;
		}
		points[positon].setEnabled(true);
		points[currentIndex].setEnabled(false);
		currentIndex = positon;
	}

	/**
	 * 通过点击事件来切换当前的页面
	 */
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_start) {
			start();
			return;
		}
	}

	@Override
	public void onBackPressed() {
		start();
	}

	private void start() {
		Intent intent = new Intent(GuideActivity.this, app.getFirstActivity());
		startActivity(intent);
		finish();
	}

}
