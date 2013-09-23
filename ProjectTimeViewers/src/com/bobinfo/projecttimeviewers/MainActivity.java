package com.bobinfo.projecttimeviewers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CancellationException;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.Toast;

import com.bobinfo.projecttimeviewers.app.BaseActivity;
import com.bobinfo.projecttimeviewers.app.Constants;
import com.bobinfo.projecttimeviewers.app.MyApplication;
import com.bobinfo.projecttimeviewers.async.AutoCancelServiceFramework;
import com.bobinfo.projecttimeviewers.exception.ResponseException;
import com.bobinfo.projecttimeviewers.modle.ProjectTimes;
import com.bobinfo.projecttimeviewers.modle.ProjectTimes.ProjectTime;
import com.bobinfo.projecttimeviewers.view.MyTemplateHeader;

public class MainActivity extends BaseActivity {
	private MyTemplateHeader header;
	private ListView showlist;
	private WebView showwebview;
	private ShowListAdapter adapter = null;
	private ProgressDialog pd = null;
	private static final String TAG = "MainActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    
    private void initView(){
    	header = (MyTemplateHeader)findViewById(R.id.head);
    	header.setHeaderTitleText("工期");
    	showlist = (ListView)findViewById(R.id.showlist);
    	showwebview = (WebView)findViewById(R.id.showwebview);
    	showlist.setDivider(null);
    	switchView(MyApplication.sessionUser.userInfo.role);
    }
    
    private void switchView(int role){
    	switch (role) {
		case 0:	//普通员工
			showlist.setVisibility(View.VISIBLE);
			showwebview.setVisibility(View.GONE);
			getProjectTimes(MyApplication.sessionUser.userInfo.id+"");
			break;
		case 1:	//领导
			showlist.setVisibility(View.GONE);
			showwebview.setVisibility(View.VISIBLE);
			showwebview.getSettings().setJavaScriptEnabled(true);
			header.setProgressBar(true);
			showwebview.setWebChromeClient(new WebChromeClient(){
		        	@Override
		        	public void onProgressChanged(WebView view, int newProgress) {
		        		MainActivity.this.setProgress(newProgress*1000);
		        	}
		        });
			showwebview.setWebViewClient(new WebViewClient(){
		        	@Override
		        	public void onReceivedError(WebView view, int errorCode,
		        			String description, String failingUrl) {
		        		Toast.makeText(MainActivity.this, description, Toast.LENGTH_SHORT).show();
		        	}
		        	@Override
		        	public void onPageFinished(WebView view, String url) {
		        		super.onPageFinished(view, url);
		        		header.setProgressBar(false);
		        	}
		        });
			showwebview.loadUrl(Constants.WEBVIEW_URL);
			break;
		default:
			break;
		}
    }
    
    private void getProjectTimes(String userId){
    	new AutoCancelServiceFramework<String, Void, ProjectTimes>(this) {
			protected void onPreExecute() {
				pd = new ProgressDialog(MainActivity.this);
				pd.setMessage("加载中，请稍后……");
				pd.setCancelable(true);
				pd.setOnCancelListener(new OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						cancel();
					}
				});
				pd.show();
			};
			protected ProjectTimes doInBackground(String... params) {
				createIPlatCokeService();
				try {
					return mIPlatService.getProjectJTimes(params[0]);
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
			protected void onPostExecute(ProjectTimes result) {
				if(result!=null){
					if(result.state==0){
						adapter = new ShowListAdapter(MainActivity.this, result);
				    	showlist.setAdapter(adapter);
					}else{
						Toast.makeText(MainActivity.this, result.msg, Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(MainActivity.this, "网络错误！", Toast.LENGTH_SHORT).show();
				}
				if(pd!=null&&pd.isShowing()&&!MainActivity.this.isFinishing()){
					pd.cancel();
				}
			};
		}.execute(userId);
    }
    
    /**
     * 测试数据
     * @return
     */
    private ProjectTimes getTestData(){
    	ProjectTimes project = new ProjectTimes();
    	List<ProjectTime> projects = new ArrayList<ProjectTime>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
    	for(int i=0;i<20;i++){
    		ProjectTime p = new ProjectTime();
        	p.time = "预定完成时间："+sdf.format(new Date());
        	p.name = "工程名：test"+i;
        	p.misstime = "延期："+sdf.format(new Date());
        	projects.add(p);
    	}
    	project.projects = projects;
    	return project;
    }
}