package com.change.mvpdemo.presenter;

import com.change.mvpdemo.modle.ILoginStatus;
import com.change.mvpdemo.modle.IStatus;
import com.change.mvpdemo.modle.IStatusCallback;
import com.change.mvpdemo.modle.impl.LoginStatus;
import com.change.mvpdemo.view.ILoginView;

/**
 * MVPģʽ�е�P(������)���������������е�ģ�ͺ���ͼ��
 * 
 * @author Change
 * 
 */
public class LoginPersenter {
	private ILoginView mLoginView;// ������ͼ����
	private ILoginStatus mStatus;// ����ģ��

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
				case ILoginStatus.STATUS_VERIFY_FAIL:// ��֤ʧ��
				case ILoginStatus.STATUS_LOGIN_FAIL:// ��½ʧ��
					mLoginView.hideLoadding();
					mLoginView.showMsg(s.getMsg());
					break;
				case ILoginStatus.STATUS_LOGIN_ING:// ��½��
					mLoginView.showLoadding();
					break;
				case ILoginStatus.STATUS_LOGIN_SUCCESS:// ��½�ɹ�
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
