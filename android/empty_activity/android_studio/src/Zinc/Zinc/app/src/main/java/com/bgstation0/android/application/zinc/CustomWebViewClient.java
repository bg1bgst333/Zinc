package com.bgstation0.android.application.zinc;

import android.app.Activity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

/**
 * Created by bg1.
 */
public class CustomWebViewClient extends WebViewClient{

    // メンバフィールドの定義.
    public Activity activity = null;    // Activityインスタンスactivityをnullにセット.

    public boolean shouldOverrideUrlLoading(WebView view, String url){
        // リンクをクリックした時に, リンク先のURLをEditTextに反映.
        if (activity != null) {  // activityがあれば.
            EditText urlBar = (EditText) activity.findViewById(R.id.urlbar);   // urlBarを取得.
            urlBar.setText(url);    // urlBar.setTextでurlを指定すると, urlBarのURLがリンク先のURLに変わる.
        }
        return super.shouldOverrideUrlLoading(view, url);
        //return false; // こっちでも大丈夫.
    }
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
        return super.shouldOverrideUrlLoading(view, request);
        //return false; // こっちでも大丈夫.
    }
}
