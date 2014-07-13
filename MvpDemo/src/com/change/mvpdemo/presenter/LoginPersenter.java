package com.change.mvpdemo.presenter;

import com.change.mvpdemo.modle.ILoginStatus;
import com.change.mvpdemo.modle.IStatus;
import com.change.mvpdemo.modle.IStatusCallback;
import com.change.mvpdemo.modle.impl.LoginStatus;
import com.change.mvpdemo.view.ILoginView;

/**
 * MVP模式中的P(主导器)，它负责主导所有的模型和视图。
 * 
 * @author Change
 * 
 */
public class LoginPersenter {
	private ILoginView mLoginView;// 持有视图对象
	private ILoginStatus mStatus;// 持有模型

	public LoginPersenter() {
		mStatus = new LoginStatus();
	}

	public void setLoginPersenterView(ILoginView _loginView) {
		this.mLoginView = _loginView;
	}

	public ILoginView getLoginPersenterView() {
		return mLoginView;
	}

	public void didLoginSuccess(String account, String psw) {
		mStatus = new LoginStatus();
		mStatus.login(account, psw, new IStatusCallback() {

			@Override
			public void onStatus(IStatus status) {
				LoginStatus s = (LoginStatus) status;
				switch (s.getStatus()) {
				case ILoginStatus.STATUS_VERIFY_FAIL:// 验证失败
				case ILoginStatus.STATUS_LOGIN_FAIL:// 登陆失败
					mLoginView.hideLoadding();
					mLoginView.showMsg(s.getMsg());
					break;
				case ILoginStatus.STATUS_LOGIN_ING:// 登陆中
					mLoginView.showLoadding();
					break;
				case ILoginStatus.STATUS_LOGIN_SUCCESS:// 登陆成功
					mLoginView.hideLoadding();
					mLoginView.moveToMain();
					break;
				default:
					break;
				}
			}
		});
	}
}
