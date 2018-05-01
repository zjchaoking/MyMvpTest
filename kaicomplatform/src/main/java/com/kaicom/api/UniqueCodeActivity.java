package com.kaicom.api;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kaicom.api.activity.BaseScanActivity;
import com.kaicom.api.preference.PlatformPreference;
import com.kaicom.api.util.StringUtil;
import com.kaicom.api.view.edittext.DeLBackEditText;
import com.kaicom.api.view.edittext.DeLBackEditText.InputLengthChangeListener;
import com.kaicom.api.view.toast.ToastTools;
import com.kaicom.fw.R;

import java.util.regex.Pattern;

@SuppressWarnings("deprecation")
public class UniqueCodeActivity extends BaseScanActivity implements
		OnClickListener {

	DeLBackEditText uniqueCodeEditText;
	Button saveUniqueCodeBtn;
	TextView inputLengthTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kl_unique_code_layout);
		initViews();
		initListener();
	}

	private void initViews() {
		uniqueCodeEditText = (DeLBackEditText) findViewById(R.id.editTextUniqueCode);
		saveUniqueCodeBtn = (Button) findViewById(R.id.buttonConfirm);
		inputLengthTextView = (TextView) findViewById(R.id.textViewInputLength);

		uniqueCodeEditText.setShowSoftInput(true);
	}

	private void initListener() {
		uniqueCodeEditText.setOnClickListener(this);
		saveUniqueCodeBtn.setOnClickListener(this);

		uniqueCodeEditText
				.setInputLengthChangeListener(new InputLengthChangeListener() {

					@Override
					public void onTextLengthChanged(int length) {
						inputLengthTextView.setText("" + length);
					}
				});
	}

	@Override
	public void onClick(View v) {
		if (v.equals(uniqueCodeEditText) || v.equals(saveUniqueCodeBtn)) {
			saveUniqueCode();
		}
	}

	@Override
	public void onScan(String result) {
		if (StringUtil.isBlank(result)) {
			uniqueCodeEditText.alertErrorToastAndSpeech("未扫描到数据");
			return;
		}
        if (!checkUniqueCode(result)) {
            return;
        }

		uniqueCodeEditText.setText(result);
		saveUniqueCode();
	}

	// 保存唯一码
	private void saveUniqueCode() {
		String uniqueCode = uniqueCodeEditText.getContent();

		if (checkUniqueCode(uniqueCode)) {
			uniqueCode = uniqueCode.trim();

			KaicomApplication.app.getKaicomJNIProxy()
					.setMachineCode(uniqueCode);
			PlatformPreference.getInstance().setMachineCode(uniqueCode);
			ToastTools.showToast("机器唯一码保存成功");
			Class<?> subject = KaicomApplication.app.getFirstActivity();
			openActivity(subject);

			finish();
		}

	}

	// 校验唯一码
	private boolean checkUniqueCode(String code) {

		if (StringUtil.isBlank(code)) {
			uniqueCodeEditText.alertErrorToastAndSpeech("唯一码不能为空");
			return false;
		}

		if (!code.trim().matches("\\d{15}")) {
			uniqueCodeEditText.alertErrorToastAndSpeech("唯一码为15位数字");
			return false;
		}
        Pattern pattern = Pattern.compile("^[1-3]\\d((0[1-9])|10|11|12)(\\d{11})$");
        if(!pattern.matcher(code).matches()){
            uniqueCodeEditText.alertErrorToastAndSpeech("唯一码非法");
            return false;
        }
		return true;
	}


	// 不允许退出
	@Override
	public void onBackPressed() {
	}

}
