/**
 * 
 */
package com.example.test;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.test.dao.DatabaseHelper;
import com.example.test.web.WebAppInterface;
import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * @author kevin.chan
 * 
 */
public class WebViewActivity extends Activity {

	private DatabaseHelper databaseHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		WebView myWebView = (WebView) findViewById(R.id.webView1);
		
		myWebView.addJavascriptInterface(new WebAppInterface(this, getHelper()), "Android");
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		myWebView.loadUrl("file:///android_asset/www/index.html");
	}

	// TODO: Page navigation
	// http://developer.android.com/guide/webapps/webview.html

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}

	private DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this,
					DatabaseHelper.class);
		}
		return databaseHelper;
	}
}
