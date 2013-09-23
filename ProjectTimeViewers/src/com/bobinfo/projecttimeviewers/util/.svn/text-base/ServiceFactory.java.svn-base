package com.bobinfo.projecttimeviewers.util;

import com.bobinfo.projecttimeviewers.service.IDownloadServices;
import com.bobinfo.projecttimeviewers.service.IPlatService;
import com.bobinfo.projecttimeviewers.service.impl.DownloadServiceAgent;
import com.bobinfo.projecttimeviewers.service.impl.PlatServiceAgent;

/**
 * 服务工厂，负责产生对应服务
 * @author Change
 *
 */
public class ServiceFactory {
	public static ServiceFactory get() {
		return cokeServiceFactory;
	}
	//生成普通请求服务
	public IPlatService createPlatService() {
		return new PlatServiceAgent();
	}
	//生成下载相关服务
	public IDownloadServices createDownloadServices(){
		return new DownloadServiceAgent();
	}
	//静态工厂实体
	private static ServiceFactory cokeServiceFactory = new ServiceFactory();
}
