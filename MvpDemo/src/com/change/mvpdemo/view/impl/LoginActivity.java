package com.change.mvpdemo.view.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.change.mvpdemo.R;
import com.change.mvpdemo.presenter.LoginPersenter;
import com.change.mvpdemo.view.ILoginView;

/**
 * 我的的activity,实现view抽象类，获得动作。
 * 
 * @author Change
 * 
 */
public class LoginActivity extends Activity implements ILoginView,
		OnClickListener {
	private ProgressDialog dialog;
	private EditText etAccount, etPsw;
	private Button btnLogin;
	private LoginPersenter mPersenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mPersenter = new LoginPersenter();
		mPersenter.setLoginPersenterView(this);
		initViews();
	}

	@Override
	public void showMsg(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void moveToMain() {
		Intent toMain = new Intent(this, MainActivity.class);
		startActivity(toMain);
	}

	@Override
	public void showLoadding() {
		dialog.show();
	}

	@Override
	public void hideLoadding() {
		dialog.cancel();
	}

	@Override
	public void initViews() {
		dialog = new ProgressDialog(this);
		dialog.setMessage("加载中。。。");
		etAccount = (EditText) findViewById(R.id.et_account);
		etPsw = (EditText) findViewById(R.id.et_psw);
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_login:
			mPersenter.didLoginSuccess(etAccount.getText().toString(), etPsw
					.getText().toString());
			break;

		default:
			break;
		}
	}

}
