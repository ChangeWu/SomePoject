package com.bobinfo.projecttimeviewers;

import java.io.IOException;
import java.util.concurrent.CancellationException;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bobinfo.projecttimeviewers.app.BaseActivity;
import com.bobinfo.projecttimeviewers.app.DLog;
import com.bobinfo.projecttimeviewers.async.AutoCancelServiceFramework;
import com.bobinfo.projecttimeviewers.exception.ResponseException;
import com.bobinfo.projecttimeviewers.modle.UserInfo;

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
		bt_login = (Button)findViewById(R.id.login_button);
		bt_login.setOnClickListener(this);
		cbx_rember = (CheckBox)findViewById(R.id.check_box);
	}
	
	private void login(String userName,String password){
		new AutoCancelServiceFramework<String, Void, UserInfo>(this) {
			protected void onPreExecute() {
				pd = new ProgressDialog(LoginActivity.this);
				pd.setMessage("登陆中，请稍后……");
				pd.setCancelable(true);
				pd.show();
			};
			protected UserInfo doInBackground(String... params) {
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
			protected void onPostExecute(UserInfo result) {
				if(result!=null){
					DLog.v(TAG, result.toString());
					if(result.state==0){
						goTo(LoginActivity.this, MainActivity.class, new Bundle());
					}
				}
				if(pd!=null&&pd.isShowing()&&!LoginActivity.this.isFinishing()){
					pd.cancel();
				}
				Toast.makeText(LoginActivity.this, result.msg, Toast.LENGTH_SHORT).show();
			};
		}.execute(userName,password);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_button:
			login(et_name.getText().toString(), et_pw.getText().toString());
			break;

		default:
			break;
		}
	}
}
