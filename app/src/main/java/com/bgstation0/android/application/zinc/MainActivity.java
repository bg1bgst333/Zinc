package com.bgstation0.android.application.zinc;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // WebViewオブジェクトwebViewを取得し, loadUrlで指定のURLをロード.
        WebView webView = (WebView)findViewById(R.id.webView);  // findViewByIdでR.id.webViewなwebViewを探して取得.
        webView.loadUrl("http://www.google.co.jp"); // loadUrlで"http://www.google.co.jp"をロード.
    }
}
