package com.bobinfo.projecttimeviewers.async;

import com.bobinfo.projecttimeviewers.app.BaseActivity;


/**
 * @author Change
 *
 * @param <Params> 参数
 * @param <Progress> 进度
 * @param <Result> 结果
 */
public abstract class AutoCancelFramework<Params,Progress,Result> extends
		AsyncFramework<Params, Progress, Result> {
	AutoCancelFramework(BaseActivity activity){
		super();
		mAutoCancelController = activity.getAutoCancelController();
	}
	
	AutoCancelFramework(AutoCancelController controller){
		super();
		mAutoCancelController = controller;
	}
	
	@Override
	protected void finish(Result result) {
		if(mAutoCancelController != null) {
			mAutoCancelController.remove(this);
			mAutoCancelController = null;
		}
		super.finish(result);
	}
	
	protected AutoCancelController mAutoCancelController;
}
