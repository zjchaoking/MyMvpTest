package com.kaicom.mymvptest.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaicom.api.util.ApkUtil;
import com.kaicom.api.util.StringUtil;
import com.kaicom.api.view.dialog.DialogTools;
import com.kaicom.api.view.toast.ToastTools;
import com.kaicom.mymvptest.R;
import com.kaicom.mymvptest.base.BaseFragment;
import com.kaicom.mymvptest.inerface.OnFragmentInteractionListener;
import com.kaicom.mymvptest.network.RetrofitManager;
import com.kaicom.mymvptest.network.response.CheckSoftUpgradeResponse;
import com.kaicom.mymvptest.ui.activity.LoginActivity;
import com.kaicom.mymvptest.utils.DialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.kaicom.mymvptest.base.BaseActivity.isLogin;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    Unbinder unbinder;
    @BindView(R.id.iv_login)
    ImageView ivLogin;
    @BindView(R.id.cv_upgrade)
    CardView cvUpgrade;
    @BindView(R.id.cv_version)
    CardView cvVersion;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_synopsis)
    TextView tvSynopsis;
    @BindView(R.id.cv_logout)
    CardView cvLogout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

    }

    @OnClick({R.id.ll_login, R.id.iv_login, R.id.cv_upgrade, R.id.cv_version, R.id.cv_logout})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_login:
            case R.id.iv_login:
                if (!isLogin()) {
                    toNextActivity(LoginActivity.class);
                }
                break;
            case R.id.cv_upgrade:
                checkVersionCode();
                break;
            case R.id.cv_version:
                DialogUtil.showSingleDialog("当前版本号:" + ApkUtil.getVersionName());
                break;
            case R.id.cv_logout:
                DialogUtil.showWarnDialog("是否确认注销？", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        filePreference.setNickname("");
                        ToastTools.showToast("注销成功！");
                        onResume();
                    }
                });
                break;
        }
    }

    /**
     * 检查软件版本号
     */
    private void checkVersionCode() {
        DialogUtil.showLoadingDialog("正在检测最新版本...", false);
        RetrofitManager.getInstance().checkSoftUpgrade("10003", ApkUtil.getVersionName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CheckSoftUpgradeResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(final CheckSoftUpgradeResponse value) {
                        DialogUtil.dismissLoadingDialog();
                        if (value.isSuccess()) {
                            DialogTools.showDialog(getActivity(), "有新版本，是否更新？", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    downApk(value.getResult().getFileName());
                                }
                            });
                        } else {
                            DialogUtil.showSingleDialog(value.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogUtil.dismissLoadingDialog();
                        DialogUtil.showSingleDialog("请求异常：" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isLogin()) {
            tvNickname.setText("注册/登录");
            tvSynopsis.setText("登录后即可体验更多服务");
            cvLogout.setVisibility(View.GONE);
        } else {
            tvNickname.setText(filePreference.getNickname());
            if(StringUtil.isEmpty(filePreference.getSynopsis())){
                tvSynopsis.setText("简介:暂无介绍");
            }else{
                tvSynopsis.setText("简介:"+filePreference.getSynopsis());
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
