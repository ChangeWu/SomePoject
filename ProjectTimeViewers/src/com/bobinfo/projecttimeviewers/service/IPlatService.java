package com.bobinfo.projecttimeviewers.service;

import java.io.IOException;
import java.util.concurrent.CancellationException;

import com.bobinfo.projecttimeviewers.exception.ResponseException;
import com.bobinfo.projecttimeviewers.modle.UserInfo;
import com.bobinfo.projecttimeviewers.util.BasicServiceParams;

/**
 * 请求服务接口
 * 
 * @author Change
 * */
public interface IPlatService extends IService<BasicServiceParams> {

	/**
	 * @param loginName
	 *            登录名
	 * @param password
	 *            密码
	 * @return 登录用户实体 以下为发送请求时异常
	 * @throws CancellationException
	 *             取消服务异常
	 * @throws IllegalArgumentException
	 *             非法参数异常
	 * @throws IOException
	 * @throws ResponseException
	 *             返回异常
	 */
	public UserInfo toLogin(String loginName, String password)
			throws CancellationException, IllegalArgumentException,
			IOException, ResponseException;

}
