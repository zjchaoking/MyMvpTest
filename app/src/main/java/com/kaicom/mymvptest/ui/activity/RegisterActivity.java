package com.kaicom.mymvptest.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kaicom.api.util.StringUtil;
import com.kaicom.api.view.toast.ToastTools;
import com.kaicom.mymvptest.R;
import com.kaicom.mymvptest.base.BaseActivity;
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

/**
 * 注册界面
 */
public class RegisterActivity extends BaseActivity {


    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected int getViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                toNextActivity(LoginActivity.class);
                onBackPressed();
                break;
            case R.id.btn_register:
                doRegister();
                break;

        }
    }

    /**
     * 注册
     */

    private void doRegister() {
        if (StringUtil.isEmpty(etUsername.getText().toString())) {
            DialogUtil.showSingleDialog("请先填写用户名!");
            return;
        }
        if (StringUtil.isEmpty(etPassword.getText().toString())) {
            DialogUtil.showSingleDialog("请先填写密码!");
            return;
        }
        DialogUtil.showLoadingDialog("正在注册...", false);
        RetrofitManager.getInstance().doRegister(getRegisterRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse value) {
                        DialogUtil.dismissLoadingDialog();
                        ToastTools.showLazzToast(value.getErrorMsg());
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

    private Map<String, Object> getRegisterRequest() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", etUsername.getText().toString());
        map.put("password", etPassword.getText().toString());
        map.put("nickname", etNickname.getText().toString());
        map.put("email", etEmail.getText().toString());
        map.put("serverCode", 10001);
        return map;
    }

}
