package com.change.mvpdemo.modle.impl;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.change.mvpdemo.modle.ILoginStatus;
import com.change.mvpdemo.modle.IStatusCallback;

/**
 * 实现类，真正的数据访问在这里。
 * 
 * @author Change
 */
public class LoginStatus implements ILoginStatus {
	private int status = ILoginStatus.STATUS_LOGIN_ING;
	private String msg = "";

	@Override
	public void login(final String account, final String psw,
			final IStatusCallback callback) {
		new AsyncTask<String, Void, ILoginStatus>() {
			@Override
			protected ILoginStatus doInBackground(String... arg0) {
				if (varify(account, psw)) {
					try {//模拟网络请求耗时处理
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if ("Change".equals(account) && "123".equals(psw)) {
						status = ILoginStatus.STATUS_LOGIN_SUCCESS;
						msg = "登陆成功";
					} else {
						status = ILoginStatus.STATUS_LOGIN_FAIL;
						msg = "登陆失败";
					}
				}
				return LoginStatus.this;
			}

			@Override
			protected void onPreExecute() {
				callback.onStatus(LoginStatus.this);
				
			}

			@Override
			protected void onPostExecute(ILoginStatus result) {
				callback.onStatus(result);
			}
		}.execute();

	}

	/**
	 * 本地校验
	 * 
	 * @param account
	 * @param psw
	 * @return
	 */
	private boolean varify(String account, String psw) {
		if (TextUtils.isEmpty(account)) {
			status = ILoginStatus.STATUS_VERIFY_FAIL;
			msg = "用户名不能为空！";
			return false;
		}
		if (TextUtils.isEmpty(psw)) {
			status = ILoginStatus.STATUS_VERIFY_FAIL;
			msg = "密码不能为空！";
			return false;
		}
		return true;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	

}
