package com.bgstation0.android.application.zinc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {

    // メンバフィールドの定義
    private View fragmentView = null;   // View型fragmentViewをnullにセット.
    private WebView webView = null; // WebView型webViewをnullにセット.
    private final String BUNDLE_ARGUMENT_KEY_URL = "url";   // 定数BUNDLE_ARGUMENT_KEY_URLを"url"とする.

    // コンストラクタ
    public WebFragment() {
        // Required empty public constructor
    }

    // ビューの生成時.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // ビューの生成.
        fragmentView = inflater.inflate(R.layout.fragment_web, container, false);   // inflater.inflateでR.layout.fragment_webをベースにfragmentViewを生成.
        // 引数の受領.
        Bundle args = getArguments();   // getArgumentsでargs取得.
        if (args != null) {
            String url = args.getString(BUNDLE_ARGUMENT_KEY_URL);   // args.getStringでurlを取得.
            if (url != null){
                loadUrl(url);   // loadUrlでurlをロード.
            }
        }
        return fragmentView;    // fragmentViewを返す.
    }

    // URLのロード.
    public void loadUrl(String url){
        // webViewを探してロードさせる.
        webView = (WebView)fragmentView.findViewById(R.id.webView);  // webViewを取得.
        webView.loadUrl(url);   // webView.loadUrlでロード.
    }

}
