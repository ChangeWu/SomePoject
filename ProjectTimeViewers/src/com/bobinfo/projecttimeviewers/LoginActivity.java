package com.bobinfo.projecttimeviewers;

import java.io.IOException;
import java.util.concurrent.CancellationException;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bobinfo.projecttimeviewers.app.BaseActivity;
import com.bobinfo.projecttimeviewers.app.DLog;
import com.bobinfo.projecttimeviewers.app.MyApplication;
import com.bobinfo.projecttimeviewers.async.AutoCancelServiceFramework;
import com.bobinfo.projecttimeviewers.exception.ResponseException;
import com.bobinfo.projecttimeviewers.modle.UserInfos;

/**
 * 登陆界面
 * @author Change
 * @date 2013-9-18[下午5:07:02]
 */
public class LoginActivity extends BaseActivity implements OnClickListener{
	private static final String TAG = "LoginActivity";
	private EditText et_name,et_pw;
	private Button bt_login;
	private CheckBox cbx_rember;
	private ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}
	
	private void initView(){
		et_name = (EditText)findViewById(R.id.acount_edit);
		et_pw = (EditText)findViewById(R.id.pass_edit);
		et_name.setText(getSharePreference().getUserInfo().userInfo.userName);
		et_pw.setText(getSharePreference().getUserInfo().userInfo.password);
		bt_login = (Button)findViewById(R.id.login_button);
		bt_login.setOnClickListener(this);
		cbx_rember = (CheckBox)findViewById(R.id.check_box);
	}
	
	/**
	 * 登陆
	 * @param userName
	 * @param password
	 */
	private void login(String userName,String password){
		autoCancel(new AutoCancelServiceFramework<String, Void, UserInfos>(this) {
			protected void onPreExecute() {
				pd = new ProgressDialog(LoginActivity.this);
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
			protected UserInfos doInBackground(String... params) {
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
			protected void onPostExecute(UserInfos result) {
				if(result!=null){
					DLog.v(TAG, result.toString());
					if(result.state==0){//登陆成功
						MyApplication.sessionUser = result;
						if(cbx_rember.isChecked()){
							getSharePreference().saveUserInfos(result);//保存用户名和密码
						}else{
							UserInfos user = new UserInfos();
							getSharePreference().saveUserInfos(user);
						}
						goTo(LoginActivity.this, MainActivity.class, new Bundle());
						DLog.v(TAG, MyApplication.sessionUser.userInfo.userName);
					}
					Toast.makeText(LoginActivity.this, result.msg, Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(LoginActivity.this, "网络错误！", Toast.LENGTH_SHORT).show();
				}
				if(pd!=null&&pd.isShowing()&&!LoginActivity.this.isFinishing()){
					pd.cancel();
				}
			};
		}.execute(userName,password));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_button:
			if(canCommit()){
				login(et_name.getText().toString(), et_pw.getText().toString());
			}
			break;

		default:
			break;
		}
	}
	
	/**
	 * 空排除
	 * @return
	 */
	private boolean canCommit(){
		if(TextUtils.isEmpty(et_name.getText().toString())){
			Toast.makeText(this, "请输入用户名！", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(TextUtils.isEmpty(et_pw.getText().toString())){
			Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}
