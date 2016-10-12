package com.bgstation0.android.application.zinc;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by bg1 on 2016/10/12.
 */

public class CustomWebChromeClient extends WebChromeClient {

    // メンバフィールドの定義
    private WebFragment webFragment = null; // WebFragment型webFragmentをnullにセット.

    // コンストラクタ
    public CustomWebChromeClient(WebFragment webFragment){
        this.webFragment = webFragment; // this.webFragmentにwebFragmentをセット.
    }

    // 進捗が変化した時.
    @Override
    public void onProgressChanged(WebView view, int progress) {
        webFragment.setProgress(progress);
    }
}
