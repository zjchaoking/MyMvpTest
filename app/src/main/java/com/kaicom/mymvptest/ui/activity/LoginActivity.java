package com.kaicom.mymvptest.ui.activity;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.kaicom.api.util.StringUtil;
import com.kaicom.api.view.toast.ToastTools;
import com.kaicom.mymvptest.R;
import com.kaicom.mymvptest.base.BaseActivity;
import com.kaicom.mymvptest.entities.User;
import com.kaicom.mymvptest.network.RetrofitManager;
import com.kaicom.mymvptest.network.response.BaseResponse;
import com.kaicom.mymvptest.utils.DialogUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected int getViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.btn_register:
                toNextActivity(RegisterActivity.class);
                onBackPressed();
                break;

        }
    }



    private void downApk(String fileName) {
//        final String filePath = SDPATH + fileName;
//        RetrofitManager.getInstance().doSoftUpgrade("10004")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Function<ResponseBody, InputStream>() {
//                    @Override
//                    public InputStream apply(ResponseBody responseBody) throws Exception {
//                        return responseBody.byteStream();
//                    }
//                })
//                .observeOn(Schedulers.computation()) // 用于计算任务
//                .doOnNext(new Consumer<InputStream>() {
//                    @Override
//                    public void accept(InputStream inputStream) throws Exception {
//                        FileUtil.writeFile(filePath, inputStream);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<InputStream>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(InputStream value) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        DialogUtil.showSingleDialog(e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


    }


    private void doLogin() {
        if (StringUtil.isEmpty(etUsername.getText().toString())) {
            DialogUtil.showSingleDialog("请先填写用户名!");
            return;
        }
        if (StringUtil.isEmpty(etPassword.getText().toString())) {
            DialogUtil.showSingleDialog("请先填写密码!");
            return;
        }
        DialogUtil.showLoadingDialog("正在登录...", false);
        RetrofitManager.getInstance().doLogin(getLoginRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse value) {
                        DialogUtil.dismissLoadingDialog();
                        if (value.isSuccess()) {
                            toNextActivity(MainMenuActivity.class);
                            User user = new Gson().fromJson(value.getErrorMsg(), User.class);
                            filePreference.setNickname(StringUtil.isEmpty(user.getNickName())?user.getUserName():user.getNickName());
                            ToastTools.showLazzToast("登录成功！");
                            onBackPressed();
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

    private Map<String, Object> getLoginRequest() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", etUsername.getText().toString());
        map.put("password", etPassword.getText().toString());
        map.put("serverCode", 10002);
        return map;
    }


    @Override
    protected void onResume() {
        super.onResume();
        etUsername.setText("jin");
        etPassword.setText("123456");
    }


}
