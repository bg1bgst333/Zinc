package com.bgstation0.android.application.zinc;


import android.app.Activity;
import android.app.DownloadManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewTabFragment extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener{

    // メンバフィールドの定義.
    View fragmentView = null;   // fragmentのView.
    public ProgressBar progressBar = null;  // ProgressBar型progressBarにnullをセット.
    public Bundle mInstanceState = null;   // メンバとしてsavedInstanceStateを保持しておくmInstanceState.
    public boolean isPC = false;    // boolean型isPCにfalseをセット.
    public String phoneUA = null; // String型phoneUAにnullをセット.
    public String pcUA = null;  // String型pcUAにnullをセット.
    public String tag = null;   // String型tagにnullをセット.

    public WebViewTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // ショートカットから渡されたURL.
        String url = null; // String型urlをnullにセット.

        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_web_view_tab, container, false);

        // タグのセット.
        if (tag == null) {  // tagが無かったら.
            Bundle bundle = getArguments(); // getArgumentsでbundleを取得.(savedInstanceStateでは渡されてこないので注意.)
            tag = bundle.getString("tag");  // tagを取得.
            fragmentView.setTag(tag);   // fragmentView.setTagでセット.
            url = bundle.getString("url");  // urlを取得.
        }

        // MainActivityを使えるようにしておく.
        MainActivity activity = (MainActivity)getActivity();  // getActivityでMainActivityを取得.

        // webViewの初期化.
        WebView webView = (WebView)fragmentView.findViewById(R.id.webview);  // webViewを取得.

        // UserAgentの取得と設定.
        if (!isPC) { // モバイル.
            phoneUA = webView.getSettings().getUserAgentString();   // webView.getSettings().getUserAgentStringでphoneUA取得.
            pcUA = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36"; // pcUAはこれ.
            webView.getSettings().setUserAgentString(phoneUA); // webView.getSettings().setUserAgentStringでphoneUAを設定.
        }
        else{
            webView.getSettings().setUserAgentString(pcUA); // webView.getSettings().setUserAgentStringでpcUAを設定.
        }

        // JavaScriptの有効化.
        webView.getSettings().setJavaScriptEnabled(true);   // webView.getSettings().setJavaScriptEnabledでtrueにすることでJavaScriptを有効にする.

        // WebViewClientのセット.
        CustomWebViewClient customwv = new CustomWebViewClient();    // CustomWebViewClientのインスタンス生成.
        customwv.fragment = this;   // customwv.fragmentにthis(WebViewTabFragment自身)をセット.
        webView.setWebViewClient(customwv);    // webView.setWebViewClientでCustomWebViewClientのインスタンスcustomwvをセット.(これをやらないと一部のサイトでChromeにリダイレクトしてしまう.)
        CustomWebChromeClient customwc = new CustomWebChromeClient();   // CustomWebChromeClientのインスタンス生成.
        customwc.fragment = this;   // customwc.fragmentにthis(MainActivity自身)をセット.
        webView.setWebChromeClient(customwc);    // webView.setWebChromeClientでCustomWebChromeClientのインスタンスcustomwcをセット.

        // mInstanceStateがあれば, それを使って復元.
        if (mInstanceState != null){   // mInstanceStateがnullでなければ.
            webView.restoreState(mInstanceState);   // webView.restoreStateでmInstanceStateから復元.
        }

        // button1を取得し, OnClickListenerとして自身をセット.
        Button button1 = (Button)fragmentView.findViewById(R.id.button1);    // R.id.button1を取得.
        button1.setOnClickListener(this);    // button1.setOnClickListenerでthis(自身)をセット.

        // urlBarを取得し, OnEditorActionListenerとして自身をセット.
        EditText urlBar = (EditText)fragmentView.findViewById(R.id.urlbar); // R.id.urlbarを取得.
        urlBar.setOnEditorActionListener(this); // urlBar.setOnEditorActionListenerでthis(自身)をセット.

        // progressBarは最初は隠しておく.
        progressBar = (ProgressBar)fragmentView.findViewById(R.id.progressBar);  // progressBarを取得.
        progressBar.setVisibility(View.INVISIBLE);  // setVisibilityでINVISIBLEにする.

        // urlがあれば初期表示.
        if (url != null) {
            String show = null; // 表示するURlのshowにnullをセット.
            if (url.startsWith("https://")) {    // "https://"の場合.
                show = url; // showにurlをそのまま代入.
            } else if (url.startsWith("http://")) {    // "http://"の場合.
                show = url.substring(7);    // showにurlの7文字目から始まる文字列を代入.
            } else {   // それ以外.
                show = url; // showにurlをそのまま代入.
            }
            urlBar.setText(show);   // urlBar.setTextでURLバーにshowをセットして表示.
            webView.loadUrl(url);   // webView.loadUrlでurlをロード.
        }

        return fragmentView;
    }

    // 配下のビューが破棄された時.
    @Override
    public void onDestroyView() {
        // webViewの状態を保存.
        WebView webView = (WebView)fragmentView.findViewById(R.id.webview);  // webViewを取得.
        mInstanceState = new Bundle();  // Bundleオブジェクトを生成.
        webView.saveState(mInstanceState); // webView.saveStateでmInstanceStateに保存.
        super.onDestroyView();
    }

    // View.OnClickListenerインタフェースのオーバーライドメソッドを実装.
    public void onClick(View v) {    // onClickをオーバーライド.

        // ボタンのidをごとに処理を振り分ける.
        switch (v.getId()) {    // v.getIdでView(Button)のidを取得.

            // R.id.button1の時.
            case R.id.button1:

                // button1ブロック
                {

                    // urlBarを空にする.
                    EditText urlBar = (EditText)fragmentView.findViewById(R.id.urlbar);    // findViewByIdでurlBarを取得.
                    urlBar.setText(""); // urlBar.setTextで""をセット.

                }

                // 抜ける.
                break;    // breakで抜ける.

            // それ以外の時.
            default:

                // 抜ける.
                break;    // breakで抜ける.

        }

    }

    // リロード.
    public void reloadPCSite(){
        // リロード.
        isPC = true;    // isPCをtrueに.
        WebView webView = (WebView) fragmentView.findViewById(R.id.webview);  // webViewを取得.
        webView.getSettings().setUserAgentString(pcUA); // webView.getSettings().setUserAgentStringでpcUAを設定.
        webView.reload();   // リロード.
    }
    // Phoneリロード.
    public void reloadPhone(){
        // リロード.
        isPC = false;   // isPCをfalseに.
        WebView webView = (WebView) fragmentView.findViewById(R.id.webview);  // webViewを取得.
        webView.getSettings().setUserAgentString(phoneUA);  // webView.getSettings().setUserAgentStringでphoneUAを設定.
        webView.reload();   // リロード.
    }

    // TextView.OnEditorActionListenerインタフェースのオーバーライドメソッドを実装.
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event){

        // MainActivityを使えるようにしておく.
        MainActivity activity = (MainActivity)getActivity();  // getActivityでMainActivityを取得.

        // Enterキーが押された時.
        if (actionId == EditorInfo.IME_ACTION_DONE){   // EditorInfo.IME_ACTION_DONEの時.

            // ソフトウェアキーボードの非表示.
            InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(activity.getApplicationContext().INPUT_METHOD_SERVICE);    // inputMethodManagerを取得.
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);  // inputMethodManager.hideSoftInputFromWindowでソフトウェアキーボードの非表示.

            // urlBarのurlを取得.
            EditText urlBar = (EditText) fragmentView.findViewById(R.id.urlbar);    // findViewByIdでurlBarを取得.
            String url = urlBar.getText().toString();    // urlBar.getText().toString()でurlを取得.

            // '.'がない, または' 'がある場合は検索文字列とする.
            if (!url.contains(".") || url.contains(" ")){
                String encoded = null;
                // URLエンコードはtryで囲む.
                try {
                    // URLエンコード.
                    encoded = URLEncoder.encode(url, "utf-8");  // URLEncoder.encodeでurlをutf-8に変換.
                }
                catch (Exception ex){
                    encoded = null;
                }
                finally {
                    // ダメならエラーメッセージ.
                    if (encoded == null){
                        Toast.makeText(activity, "URLEncode Error!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        // よければ検索.
                        String searchUrl = "https://www.google.co.jp/search?q=" + encoded;  // 実際にはURLエンコードしなくても検索できたが一応encodedを検索クエリに付ける.
                        WebView webView = (WebView) fragmentView.findViewById(R.id.webview);    // findViewByIdでwebViewを取得.
                        urlBar.setText(searchUrl);  // urlBar.setTextでURLバーにsearchUrlをセットして表示.
                        webView.loadUrl(searchUrl); // webView.loadUrlにsearchUrlを渡して検索.
                    }
                }
            }
            else {  // それ以外ならURL.

                // WebViewオブジェクトwebViewでurlのサイトを表示.
                WebView webView = (WebView) fragmentView.findViewById(R.id.webview);    // findViewByIdでwebViewを取得.
                String load = null; // ロードするURLのloadをnullにセット.
                String show = null; // 表示するURlのshowをurlにセット.
                if (url.startsWith("https://")) {    // "https://"の場合.
                    load = url; // loadにurlをそのまま代入.
                    show = url; // showにurlをそのまま代入.
                } else if (url.startsWith("http://")) {    // "http://"の場合.
                    load = url; // loadにurlをそのまま代入.
                    show = url.substring(7);    // showにurlの7文字目から始まる文字列を代入.
                } else {   // それ以外.
                    load = "http://" + url; // urlの頭に"http"を付けてloadに代入.
                    show = url; // showにurlをそのまま代入.
                }
                urlBar.setText(show);   // urlBar.setTextでURLバーにshowをセットして表示.
                webView.loadUrl(load);    // webView.loadUrlでloadのサイトを表示.

            }

            // trueを返す.
            return true;

        }

        // falseを返す.
        return false;

    }
}
