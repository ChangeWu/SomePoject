package com.bobinfo.projecttimeviewers.service.impl;

import java.io.IOException;
import java.util.concurrent.CancellationException;

import com.bobinfo.projecttimeviewers.exception.ResponseException;
import com.bobinfo.projecttimeviewers.modle.BaseInfo;
import com.bobinfo.projecttimeviewers.modle.ProjectTimes;
import com.bobinfo.projecttimeviewers.modle.UserInfos;
import com.bobinfo.projecttimeviewers.request.impl.GetProjectTimeRequest;
import com.bobinfo.projecttimeviewers.request.impl.LoginRequest;
import com.bobinfo.projecttimeviewers.request.impl.UploadPicRequest;
import com.bobinfo.projecttimeviewers.service.IPlatService;
import com.bobinfo.projecttimeviewers.util.BasicServiceParams;



/**
 * 请求服务实现
 * @author Change
 * 
 */
public class PlatServiceAgent extends
		AbstractService<BasicServiceParams> implements IPlatService {
	/**
	 * 连接时间
	 */
	private final static int DEFAULT_CONN_TIME_OUT = 15000;
	/**
	 * 发送时间
	 */
	private final static int DEFAULT_SEND_TIME_OUT = 20000;
	/**
	 * 接收时间
	 */
	private final static int DEFAULT_RECV_TIME_OUT = 30000;

	/**
	 * 初始化服务相关参数
	 */
	public PlatServiceAgent() {
		super();
		mParams = new BasicServiceParams();
		mParams.setDefaultConnTimeout(DEFAULT_CONN_TIME_OUT);
		mParams.setDefaultSendTimeout(DEFAULT_SEND_TIME_OUT);
		mParams.setDefaultRecvTimeout(DEFAULT_RECV_TIME_OUT);
		applyServiceParams();
	}

	/* (non-Javadoc)
	 * @see andr.paiyao.netaffair.api.IPlatService#toLogin(java.lang.String, java.lang.String)
	 */
	@Override
	public UserInfos toLogin(String loginName, String password)
			throws CancellationException, IllegalArgumentException,
			IOException, ResponseException {
		return Send(new LoginRequest(loginName, password));
	}

	@Override
	public ProjectTimes getProjectJTimes(String userId)
			throws CancellationException, IllegalArgumentException,
			IOException, ResponseException {
		return Send(new GetProjectTimeRequest(userId));
	}
	
	@Override
	public BaseInfo uploadImage(String fileName, String contentStr)
			throws CancellationException, IllegalArgumentException,
			IOException, ResponseException {
		return Send(new UploadPicRequest(fileName, contentStr));
	}

}
