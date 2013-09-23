package com.bobinfo.projecttimeviewers.async;

import java.util.concurrent.CancellationException;

import com.bobinfo.projecttimeviewers.app.BaseActivity;
import com.bobinfo.projecttimeviewers.service.IDownloadServices;
import com.bobinfo.projecttimeviewers.service.IPlatService;
import com.bobinfo.projecttimeviewers.util.ServiceFactory;

/**
 * @author Change
 *
 * @param <Params> 参数
 * @param <Progress> 进度
 * @param <Result> 结果
 */
public abstract class AutoCancelServiceFramework<Params, Progress, Result>
		extends AutoCancelFramework<Params, Progress, Result> {

	public AutoCancelServiceFramework(AutoCancelController controller) {
		super(controller);
		// TODO Auto-generated constructor stub
	}

	public AutoCancelServiceFramework(BaseActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	protected synchronized void createIPlatCokeService()
			throws CancellationException {
		if (isCanceled()) {
			throw new CancellationException();
		}
		if (mIPlatService != null) {
			return;
		}
		mIPlatService = ServiceFactory.get().createPlatService();
	}

	protected synchronized void createIDownloadServices()
			throws CancellationException {
		if (isCanceled()) {
			throw new CancellationException();
		}
		if (mIDownloadServices != null) {
			return;
		}
		mIDownloadServices = ServiceFactory.get().createDownloadServices();
	}
	

	@Override
	protected void finish(Result result) {
		// TODO Auto-generated method stub
		super.finish(result);
		releaseAllServices();
	}

	private void releaseAllServices() {
		synchronized (this) {
			if (mIPlatService != null) {
				mIPlatService = null;
			}
			if (mIDownloadServices != null) {
				mIDownloadServices = null;
			}
		}
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		super.cancel();
		synchronized (this) {
			if (mIPlatService != null) {
				mIPlatService.abortService();
			}
			if (mIDownloadServices != null) {
				mIDownloadServices.abortService();
			}
		}
	}

	@Override
	protected Result doInBackground(Params... params) {
		// TODO Auto-generated method stub
		return null;
	}

	protected IPlatService mIPlatService;
	protected IDownloadServices mIDownloadServices;
}
