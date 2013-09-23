package com.bobinfo.projecttimeviewers.request.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CancellationException;

import org.apache.http.client.ClientProtocolException;

import com.bobinfo.projecttimeviewers.app.Constants;
import com.bobinfo.projecttimeviewers.app.DLog;
import com.bobinfo.projecttimeviewers.exception.ResponseException;
import com.bobinfo.projecttimeviewers.modle.ProjectTimes;
import com.bobinfo.projecttimeviewers.request.AbstractRequest;
import com.bobinfo.projecttimeviewers.util.StringHelper;
import com.google.gson.Gson;

public class GetProjectTimeRequest extends AbstractRequest<ProjectTimes> {
	public static final String REQUEST_URL = Constants.SERVER_URL+"getProjectTime.php";
	public static final String TAG = "GetProjectTimeRequest";
	public GetProjectTimeRequest(String userId) {
		super(METHOD_POST);
		setRequestParam("userId", userId);
	}

	@Override
	public ProjectTimes send() throws ClientProtocolException, IOException,
			CancellationException, IllegalArgumentException, ResponseException {
		InputStream response = send(REQUEST_URL);
		Gson gson = new Gson();
		String json = StringHelper.InputStreamToString(response);
		DLog.v(TAG, json);
		return gson.fromJson(json, ProjectTimes.class);
	}
	
}
