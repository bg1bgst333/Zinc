package com.bgstation0.android.application.zinc;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by bg1 on 2016/07/14.
 */
public class CustomWebViewClient extends WebViewClient{
    public boolean shouldOverrideUrlLoading(WebView view, String url){
        return super.shouldOverrideUrlLoading(view, url);
        //return false; // こっちでも大丈夫.
    }
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
        return super.shouldOverrideUrlLoading(view, request);
        //return false; // こっちでも大丈夫.
    }
}
