package com.bobinfo.projecttimeviewers.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CancellationException;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.bobinfo.projecttimeviewers.R;
import com.bobinfo.projecttimeviewers.app.BaseActivity;
import com.bobinfo.projecttimeviewers.async.AutoCancelServiceFramework;
import com.bobinfo.projecttimeviewers.exception.ResponseException;
import com.bobinfo.projecttimeviewers.modle.BaseInfo;
import com.bobinfo.projecttimeviewers.util.BitmapFileUtils;

public class UploadImgTestActivity extends BaseActivity implements OnClickListener{
	private ProgressDialog pd;
	private Button upload;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_uploadimg);
		upload = (Button)findViewById(R.id.upload);
		upload.setOnClickListener(this);
	}
	private void upload(String fileName,String contentStr){
		autoCancel(new AutoCancelServiceFramework<String, Void, BaseInfo>(this) {
			protected void onPreExecute() {
				pd = new ProgressDialog(UploadImgTestActivity.this);
				pd.setMessage("登陆中，请稍后……");
				pd.setCancelable(true);
				pd.setOnCancelListener(new OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						cancel();
					}
				});
				pd.show();
			};
			protected BaseInfo doInBackground(String... params) {
				createIPlatCokeService();
				try {
					return mIPlatService.toLogin(params[0], params[1]);
				} catch (CancellationException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ResponseException e) {
					e.printStackTrace();
				}
				return null;
			};
			protected void onPostExecute(BaseInfo result) {
				if(result!=null){
					
				}else{
					Toast.makeText(UploadImgTestActivity.this, "网络错误！", Toast.LENGTH_SHORT).show();
				}
				if(pd!=null&&pd.isShowing()&&!UploadImgTestActivity.this.isFinishing()){
					pd.cancel();
				}
			};
		}.execute(fileName,contentStr));
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.upload:
			AssetManager am = getAssets();
			InputStream is;
			try {
				is = am.open("test.png");
				String str = BitmapFileUtils.getBitmapStrBase64(is);
				upload("test.png",str);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}
}
