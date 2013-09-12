package com.bobinfo.roomhelper.ui;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bobinfo.roomhelper.R;
import com.bobinfo.roomhelper.net.model.SonKbmNodeDto;
import com.bobinfo.roomhelper.utils.Constant;
import com.bobinfo.roomhelper.view.ExitDialog;

public class KbmDetailActivity extends BaseActivity {



	private LinearLayout linearLayoutwebview;

	private ProgressBar loadingProgress;

	
	private WebView appView;
	

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webview);
		

		String title = getIntent().getStringExtra("Title");
		String url = getIntent().getStringExtra("Url");
		
		TextView text_title = (TextView)findViewById(R.id.title);
		text_title.setText(title);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 竖屏
		linearLayoutwebview = (LinearLayout) findViewById(R.id.linearLayoutwebview);
		loadingProgress = (ProgressBar) findViewById(R.id.progress);
		appView = (WebView) findViewById(R.id.webview);
		initWebView(appView);
		appView.setWebViewClient(new WebViewClient() {  
	        @Override  
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {  
	          boolean shouldOverride = false;  
	          if (url.startsWith("https://")) { //NON-NLS  
	            // DO SOMETHING  
	            shouldOverride = true;  
	          }  
	          return shouldOverride;  
	        }  
	      });
		appView.loadUrl(url);

	}
	
	

	private void initWebView(WebView webView) {
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // 隱藏滚动条
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.addJavascriptInterface(this, "ScriptHandler");
		webSettings.setAllowFileAccess(true);
		//webSettings.setBuiltInZoomControls(true);
		// 设置高分辨率 arget-densitydpi=high-dpi
		
		
		//FIXME 版本问题 注释 CWZ 20130325
		//webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
		webSettings.setDatabaseEnabled(true);
		String dir = this.getApplicationContext()
				.getDir("database", Context.MODE_PRIVATE).getPath();
		webSettings.setDatabasePath(dir);
		
		//FIXME 版本问题 注释 CWZ 20130325
		//webSettings.setDomStorageEnabled(true);
		webSettings.setGeolocationEnabled(true);
		webSettings.setGeolocationDatabasePath(dir);
		webSettings.setSupportMultipleWindows(true);
		webView.setWebChromeClient(new WebChromeClient() {
			   public void onProgressChanged(WebView view, int progress) {
				     if(progress == 100){
				    	 view.setVisibility(View.VISIBLE);
				    	 loadingProgress.setVisibility(View.GONE);
				     }
			 }
		});

		
	}
	

	
}