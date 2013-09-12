package com.change.jstest;

import java.util.HashMap;

import android.os.Handler;
import android.webkit.WebView;


/**
 * js处理类
 * @author Change
 * @date 2013-9-12[下午2:25:49]
 */
public class JavascriptHelper {
	private WebView wv;
	private Handler handler;
	private static HashMap<WebView,JavascriptHelper> helpers = new HashMap<WebView, JavascriptHelper>();
	private JavascriptHelper(WebView wv) {
		this.wv = wv;
		wv.getSettings().setJavaScriptEnabled(true);	//设定webview支持js
		handler = new Handler();
	}
	public static JavascriptHelper getInstance(WebView wv){
		JavascriptHelper jHelper = helpers.get(wv);
		if(jHelper==null){
			jHelper = new JavascriptHelper(wv);
			helpers.put(wv, jHelper);
		}
		return jHelper;
	}
	
	/**
	 * 添加js可调用的java方法
	 * @param javaObjName 在js中用到的对象名
	 * @param inter js执行的java方法回调
	 */
	public void addJavascriptInterface(String javaObjName,final JavascriptInterface inter){
		 wv.addJavascriptInterface(new Object(){
				public void javaMethod(){	
					handler.post(new Runnable() {
						@Override
						public void run() {
							inter.onExecuteJava();
						}
					});
	        	}
	        }, javaObjName);	
	}
	
	/**
	 * java调用js方法
	 * @param methodName js方法名
	 * @param jsParams 方法参数
	 */
	public void execJSMethod(String methodName,String jsParams){
		wv.loadUrl("javascript:"+methodName+"("+jsParams+")");	//java调用js中的方法
	}
	
	
	/**
	 * 执行java方法的回调接口
	 * @author Change
	 * @date 2013-9-12[下午2:28:11]
	 */
	public interface JavascriptInterface{
		public void onExecuteJava();
	}
	
}
