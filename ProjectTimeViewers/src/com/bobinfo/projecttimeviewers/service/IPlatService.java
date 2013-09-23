package com.bobinfo.projecttimeviewers.service;

import java.io.IOException;
import java.util.concurrent.CancellationException;

import com.bobinfo.projecttimeviewers.exception.ResponseException;
import com.bobinfo.projecttimeviewers.modle.BaseInfo;
import com.bobinfo.projecttimeviewers.modle.ProjectTimes;
import com.bobinfo.projecttimeviewers.modle.UserInfos;
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
	public UserInfos toLogin(String loginName, String password)
			throws CancellationException, IllegalArgumentException,
			IOException, ResponseException;

	/**
	 * 获得工期列表
	 * @param userId
	 * @return
	 * @throws CancellationException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws ResponseException
	 */
	public ProjectTimes getProjectJTimes(String userId) throws CancellationException, IllegalArgumentException,
	IOException, ResponseException;
	
	/**
	 * 上传图片
	 * @param fileName
	 * @param contentStr
	 * @return
	 * @throws CancellationException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws ResponseException
	 */
	public BaseInfo uploadImage(String fileName,String contentStr)  throws CancellationException, IllegalArgumentException,
	IOException, ResponseException;
}
