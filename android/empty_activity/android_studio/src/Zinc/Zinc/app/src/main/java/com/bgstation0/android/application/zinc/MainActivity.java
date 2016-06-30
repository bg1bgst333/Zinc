package com.bgstation0.android.application.zinc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {	// View.OnClickListenerを実装.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // WebViewClientのセット.
        WebView webView = (WebView)findViewById(R.id.webview);  // webViewを取得.
        webView.setWebViewClient(new CustomWebViewClient());    // setWebViewClientでCustomWebViewClientのインスタンスをセット.(これをやらないと一部のサイトでChromeにリダイレクトしてしまう.)

        // Button1を取得し, OnClickListenerとして自身をセット.
        Button button1 = (Button)findViewById(R.id.button1);	// R.id.button1を取得.
        button1.setOnClickListener(this);	// button1.setOnClickListenerでthis(自身)をセット.
    }

    // View.OnClickListenerインタフェースのオーバーライドメソッドを実装.
    public void onClick(View v){	// onClickをオーバーライド.

        // ボタンのidをごとに処理を振り分ける.
        switch (v.getId()) {	// v.getIdでView(Button)のidを取得.

            // R.id.button1の時.
            case R.id.button1:

                // button1ブロック
                {

                    // urlBarのurlを取得.
                    EditText urlBar = (EditText)findViewById(R.id.urlbar);	// findViewByIdでurlBarを取得.
                    String url = urlBar.getText().toString();	// urlBar.getText().toString()でurlを取得.

                    // WebViewオブジェクトwebViewでurlのサイトを表示.
                    WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでwebViewを取得.
                    webView.loadUrl(url);	// webView.loadUrlでurlのサイトを表示.

                }

                // 抜ける.
                break;	// breakで抜ける.

            // それ以外の時.
            default:

                // 抜ける.
                break;	// breakで抜ける.

        }

    }
}