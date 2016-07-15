package com.bgstation0.android.application.zinc;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by bg1.
 */
public class CustomWebChromeClient extends WebChromeClient{

    // メンバフィールドの定義.
    public MainActivity activity = null;    // Activityインスタンスactivityをnullにセット.

    // 進捗度が変化した時.
    @Override
    public void onProgressChanged(WebView view, int progress){

        // progressBarがあれば, 進捗をセット.
        if (activity.progressBar != null){  // activity.progressBarがnullでない.
            activity.progressBar.setProgress(progress); // activity.progressBar.setProgressにprogressをセット.
        }

    }
}
