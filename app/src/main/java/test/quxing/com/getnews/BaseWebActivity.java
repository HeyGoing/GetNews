package test.quxing.com.getnews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * @Description:WebView界面，带自定义进度条显示
 * @author http://blog.csdn.net/finddreams
 */ 
public class BaseWebActivity extends AppCompatActivity {

	private WebView webview;
	private ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baseweb);

		pd=new ProgressDialog(this);
		pd.setMessage("正在加载...");

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String url = bundle.getString("url");

		go(url);
		//webview的简单设置

	}

	public void go(String url){
		webview=(WebView) findViewById(R.id.wv_internet);
		webview.loadUrl(url);//加载网页
		WebSettings websettings=webview.getSettings();
		websettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
		websettings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
		websettings.setSupportZoom(true);//支持缩放
		websettings.setBuiltInZoomControls(true);//设置支持缩放
		webview.setWebViewClient(new WebViewClient(){

			/**
			 * //这个事件就是开始载入页面调用的，通常我们可以在这设定一个loading的页面，告诉用户程序在等待网络响应。
			 *
             */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				pd.show();
			}

			/**
			 * //在页面加载结束时调用。同样道理，我们知道一个页面载入完成，于是我们可以关闭loading 条，切换程序动作。

             */
			@Override
			public void onPageFinished(WebView view, String url) {
				pd.dismiss();
			}
		});
	}
	//后退键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode== KeyEvent.KEYCODE_BACK&&webview.canGoBack()){
			webview.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	//菜单键
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "刷新");
		menu.add(0, 0, 1, "后退");
		menu.add(0, 0, 2, "前进");
		return super.onCreateOptionsMenu(menu);
	}
	//菜单点击事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getOrder()) {
			case 0:
				webview.reload();
				break;
			case 1:
				if(webview.canGoBack()){
					webview.goBack();
				}
				break;
			case 2:
				if(webview.canGoForward()){
					webview.goForward();
				}
				break;
		}
		return super.onOptionsItemSelected(item);
	}

}
