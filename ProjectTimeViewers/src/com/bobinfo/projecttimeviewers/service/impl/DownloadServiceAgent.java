package com.bobinfo.projecttimeviewers.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CancellationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;

import com.bobinfo.projecttimeviewers.service.IDownloadServices;
import com.bobinfo.projecttimeviewers.service.IDownloadServices.IDownloadObserver;
import com.bobinfo.projecttimeviewers.util.AssistInputStream;
import com.bobinfo.projecttimeviewers.util.BasicServiceParams;

/**
 * 下载服务实现
 * 
 * @author Oisny
 * 
 */
public class DownloadServiceAgent extends
		AbstractService<BasicServiceParams> implements IDownloadServices {

	/**
	 * 
	 */
	private final static long DOWNLOAD_BYTES_TO_PUBLISH = 16 * 1024;
	/**
	 * 
	 */
	private final static long DOWNLOAD_PUBLISH_INTERVAL = 1500;
	private final static int DEFAULT_CONN_TIME_OUT = 15000;
	private final static int DEFAULT_SEND_TIME_OUT = 20000;
	private final static int DEFAULT_RECV_TIME_OUT = 30000;

	/**
	 * 初始化参数
	 */
	public DownloadServiceAgent() {
		super();
		mParams = new BasicServiceParams();
		mParams.setDefaultConnTimeout(DEFAULT_CONN_TIME_OUT);
		mParams.setDefaultSendTimeout(DEFAULT_SEND_TIME_OUT);
		mParams.setDefaultRecvTimeout(DEFAULT_RECV_TIME_OUT);
	}

	@Override
	public long download(String url, long bytesOffset, long bytesToDownload,
			OutputStream outStream, IDownloadObserver observer)
			throws IOException, CancellationException {
		//建立请求
		HttpGet request = new HttpGet(url);
		//设置头信息
		if (bytesOffset > 0 || bytesToDownload > 0) {
			request.setHeader("Range", "bytes=" + bytesOffset + "-"
					+ ((bytesToDownload > 0) ? (bytesToDownload - 1) : ""));
		}
		//完成字节数
		long completedBytes = bytesOffset;
		try {
			//执行请求获得返回对象
			HttpResponse response = mHttpClient.execute(request);
			//获得返回码
			int statusCode = response.getStatusLine().getStatusCode();
			//判断失败
			if (statusCode < 200 || statusCode >= 300) {
				throw new HttpResponseException(statusCode, response
						.getStatusLine().getReasonPhrase());
			}
			if (observer != null) {
				observer.onConnect(this);
			}
			//成功，从返回对象实体中获得流
			InputStream is = response.getEntity().getContent();
			if (is == null) {
				return 0;
			}
			//追踪流内容
			is = new AssistInputStream(is, false);
			try {
				byte buf[] = new byte[1024];
				long lastPublishBytes = 0;
				long lastPublishTime = System.currentTimeMillis();

				while (!mAborted) {
					int numread = is.read(buf);
					if (numread <= 0) {
						if (observer != null) {
							if (completedBytes != lastPublishBytes) {
								//监视完成字节数
								observer.onProgress(this, completedBytes, 0);
							}
						}
						break;
					}
					outStream.write(buf, 0, numread);
					completedBytes += numread;
					if (observer != null) {
						long curTime = System.currentTimeMillis();
						if (completedBytes - lastPublishBytes >= DOWNLOAD_BYTES_TO_PUBLISH
								|| curTime - lastPublishTime >= DOWNLOAD_PUBLISH_INTERVAL) {
							observer.onProgress(this, completedBytes, 0);
							lastPublishBytes = completedBytes;
							lastPublishTime = curTime;
						}
					}
				}
			} finally {
				is.close();
			}
		} finally {
			request.abort();
			request = null;
		}
		if (mAborted) {
			throw new CancellationException();
		}
		return completedBytes;
	}
}
