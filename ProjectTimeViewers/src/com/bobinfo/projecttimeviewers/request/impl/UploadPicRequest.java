package com.bobinfo.projecttimeviewers.request.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CancellationException;

import org.apache.http.client.ClientProtocolException;

import com.bobinfo.projecttimeviewers.app.Constants;
import com.bobinfo.projecttimeviewers.app.DLog;
import com.bobinfo.projecttimeviewers.exception.ResponseException;
import com.bobinfo.projecttimeviewers.modle.BaseInfo;
import com.bobinfo.projecttimeviewers.request.AbstractRequest;
import com.bobinfo.projecttimeviewers.util.StringHelper;
import com.google.gson.Gson;

public class UploadPicRequest extends AbstractRequest<BaseInfo>{
	public static final String REQUEST_URL = Constants.SERVER_URL+"Upload.php";
	public static final String TAG = "UploadPicRequest";
	public UploadPicRequest(String fileName,String contentStr) {
		super(METHOD_POST);
		setRequestParam("fileName", fileName);
		setRequestParam("contentStr", fileName);
	}

	@Override
	public BaseInfo send() throws ClientProtocolException, IOException,
			CancellationException, IllegalArgumentException, ResponseException {
		InputStream response = send(REQUEST_URL);
		Gson gson = new Gson();
		String json = StringHelper.InputStreamToString(response);
		DLog.v(TAG, json);
		return gson.fromJson(json, BaseInfo.class);
	}
	
	
}
