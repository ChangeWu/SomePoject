package com.bobinfo.projecttimeviewers.request.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CancellationException;

import org.apache.http.client.ClientProtocolException;

import com.bobinfo.projecttimeviewers.app.Constants;
import com.bobinfo.projecttimeviewers.app.DLog;
import com.bobinfo.projecttimeviewers.exception.ResponseException;
import com.bobinfo.projecttimeviewers.modle.UserInfo;
import com.bobinfo.projecttimeviewers.request.AbstractRequest;
import com.bobinfo.projecttimeviewers.util.StringHelper;
import com.google.gson.Gson;

/**
 * 登陆请求
 * @author Change
 * @date 2013-9-18[上午11:46:10]
 */
public class LoginRequest extends AbstractRequest<UserInfo> {
	public static final String REQUEST_URL = Constants.SERVER_URL+"Login.php";
	public static final String TAG = "LoginRequest";
	public LoginRequest(String userName,String password) {
		super(METHOD_POST);
		setRequestParam("userName", userName);
		setRequestParam("password", password);
		
	}

	@Override
	public UserInfo send() throws ClientProtocolException, IOException,
			CancellationException, IllegalArgumentException, ResponseException {
		InputStream response = send(REQUEST_URL);
		Gson gson = new Gson();
		String json = StringHelper.InputStreamToString(response);
		DLog.v(TAG, json);
		return gson.fromJson(json, UserInfo.class);
	}
	
}
