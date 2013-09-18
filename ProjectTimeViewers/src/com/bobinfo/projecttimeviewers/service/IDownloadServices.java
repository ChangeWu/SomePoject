package com.bobinfo.projecttimeviewers.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CancellationException;

import com.bobinfo.projecttimeviewers.util.BasicServiceParams;

/**
 * 下载服务接口
 * 
 * @author Oisny
 * 
 */
public interface IDownloadServices extends IService<BasicServiceParams> {

	/**
	 * 回调观察者接口
	 * @author Oisny
	 * 
	 */
	public interface IDownloadObserver {
		public void onConnect(IDownloadServices services);

		public void onProgress(IDownloadServices services, long completedBytes,
				long tcRate);
	}

	/**
	 * @param url
	 *            下载地址
	 * @param bytesOffset
	 *            下载字节位置
	 * @param bytesToDownload
	 *            下载字节数
	 * @param outStream
	 *            输出流
	 * @param observer
	 *            下载观察者
	 * @return 下载字节数
	 * @throws IOException
	 * @throws CancellationException
	 */
	public long download(String url, long bytesOffset, long bytesToDownload,
			OutputStream outStream, IDownloadObserver observer)
			throws IOException, CancellationException;
}
