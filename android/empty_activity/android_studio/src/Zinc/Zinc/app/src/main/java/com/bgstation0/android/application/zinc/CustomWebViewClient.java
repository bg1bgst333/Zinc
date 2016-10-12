package com.bgstation0.android.application.zinc;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by bg1 on 2016/10/12.
 */

public class CustomWebViewClient extends WebViewClient {

    // メンバフィールドの定義
    private MainActivity mainActivity = null;   // MainActivity型mainActivityをnullにセット.
    private WebFragment webFragment = null; // WebFragment型webFragmentをnullにセット.

    // コンストラクタ
    public CustomWebViewClient(MainActivity mainActivity, WebFragment webFragment){
        this.mainActivity = mainActivity;
        this.webFragment = webFragment;
    }

    // リンクURLクリックやリダイレクトなどでロードURLが上書きされた時.
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url){
        mainActivity.setMenuUrlBar(url);
        return super.shouldOverrideUrlLoading(view, url);
    }

    // ページのロードを開始する時.
    @Override
    public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        webFragment.setProgressBarVisibility(true); // webFragment.setProgressBarVisibilityで表示.
    }

    // ページのロードが終了した時.
    @Override
    public void onPageFinished (WebView view, String url) {
        super.onPageFinished(view, url);
        webFragment.setProgressBarVisibility(false);    // webFragment.setProgressBarVisibilityで非表示.
    }
}
