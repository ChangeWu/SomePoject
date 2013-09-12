package com.change.jstest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.change.jstest.JavascriptHelper.JavascriptInterface;


/**
 * @author Change
 *	测试java代码与javascript相互调用
 */
public class JSTestActivity extends Activity {
	private WebView wv;
	private JavascriptHelper helper;
	private Activity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.main);
        activity = this;
        wv = (WebView)findViewById(R.id.webview);
        wv.getSettings().setJavaScriptEnabled(true);
        helper = JavascriptHelper.getInstance(wv);
        helper.addJavascriptInterface("caller", new JavascriptInterface(){
        	@Override
        	public void onExecuteJava() {
        		helper.execJSMethod("personlist", request());
        	}
        });
        wv.setWebChromeClient(new WebChromeClient(){
        	@Override
        	public void onProgressChanged(WebView view, int newProgress) {
        		activity.setProgress(newProgress*1000);
        	}
        });
        wv.setWebViewClient(new WebViewClient(){
        	@Override
        	public void onReceivedError(WebView view, int errorCode,
        			String description, String failingUrl) {
        		Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
        	}
        });
        wv.loadUrl("file:///android_asset/jstest.html");
    }
    
    public String request(){
    	JSONArray ja = new JSONArray();
   
    	try {
    	 	JSONObject jo1 = new JSONObject();
			jo1.put("id", "1002");
			jo1.put("name", "change");
			jo1.put("age", "23");
			jo1.put("address", "china");
			ja.put(jo1);
			JSONObject jo2 = new JSONObject();
			jo2.put("id", "1003");
			jo2.put("name", "ch");
			jo2.put("age", "22");
			jo2.put("address", "china");
			ja.put(jo2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return ja.toString();
    }
    
}