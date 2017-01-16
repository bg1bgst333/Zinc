package com.bgstation0.android.application.zinc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

// メインアクティビティ
public class MainActivity extends Activity implements View.OnClickListener{ // View.OnClickListenerを実装.

    // 作成時.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // navigateButtonを取得し, OnClickListenerとして自身をセット.
        Button navigateButton = (Button)findViewById(R.id.navigateButton);  // navigateButtonを取得.
        navigateButton.setOnClickListener(this);    // setOnClickListenerにthis(自身)をセット.
    }

    // View.OnClickListenerインタフェースのオーバーライドメソッドを実装.
    public void onClick(View v){    // onClickをオーバーライド.

        // ボタンのidごとに処理を振り分ける.
        switch (v.getId()){ // v.getIdでView(Button)のidを取得.

            // navigateButton
            case R.id.navigateButton:   // R.id.navigateButtonの時.

                // navigateButtonブロック
                {

                    // urlBarの持つURLを取得.
                    EditText urlBar = (EditText) findViewById(R.id.urlBar);  // urlBarを取得.
                    String url = urlBar.getText().toString();   // urlBar.getText().toString()でurlを取得.

                    // webViewでurlのWebページを表示.
                    WebView webView = (WebView) findViewById(R.id.webView);  // webViewを取得.
                    webView.loadUrl(url);   // loadUrlでurlをロード.

                }

                // 抜ける.
                break;  // breakを抜ける.

            default:

                // 抜ける.
                break;  // breakを抜ける.

        }

    }

}
