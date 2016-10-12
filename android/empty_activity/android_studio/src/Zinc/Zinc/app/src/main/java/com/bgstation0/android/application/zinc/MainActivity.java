package com.bgstation0.android.application.zinc;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    // メンバフィールドの定義
    private FragmentManager fragmentManager = null; // FragmentManager型fragmentManagerをnullにセット.
    private final String FRAGMENT_TAB_PREFIX_WEB = "web";   // 定数FRAGMENT_TAG_PREFIX_WEBを"web"とする.
    private final String BUNDLE_ARGUMENT_KEY_URL = "url";   // 定数BUNDLE_ARGUMENT_KEY_URLを"url"とする.
    private final String HTTP_URL_BG1_BLOG = "http://bg1.hatenablog.com";   // 定数HTTP_URL_BG1_BLOGを"http://bg1.hatenablog.com"とする.
    private int webFragmentNo = 0;  // int型webFragmentNoを0にセット.
    private String currentFragmentTag = null;   // String型currentFragmentTagをnullにセット.
    private MenuItem menuItemUrlBar = null; // MenuItem型menuItemUrlBarをnullにセット.
    private EditText menuUrlBar = null; // EditText型menuUrlBarをnullにセット.

    // アクティビティの生成時.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 最初のWebフラグメントの追加.
        fragmentManager = getSupportFragmentManager();  // getSupportFragmentManagerでfragmentManagerを取得.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();   // fragmentManager.beginTransactionでfragmentTransaction取得.
        String fragmentTag = FRAGMENT_TAB_PREFIX_WEB + webFragmentNo;   // fragmentTagはFRAGMENT_TAG_PREFIX_WEBにwebFragmentNoを付けたものとする.
        WebFragment webFragment = new WebFragment();    // WebFragmentオブジェクトwebFragmentの生成.
        //Bundle args = new Bundle(); // Bundle型argsを生成.
        //args.putString(BUNDLE_ARGUMENT_KEY_URL, HTTP_URL_BG1_BLOG); // args.putStringでBUNDLE_ARGUMENT_KEY_URLをキー, HTTP_URL_BG1_BLOGを値として登録.
        //webFragment.setArguments(args); // webFragment.setArgumentsでargsを渡す.
        fragmentTransaction.add(R.id.content, webFragment, fragmentTag);    // fragmentTransaction.addでwebFragmentをfragmentTagを付けてR.id.contentに追加.
        fragmentTransaction.commit();   // fragmentTransaction.commitで確定.
        currentFragmentTag = fragmentTag;   // currentFragmentTagにfragmentTagを格納.
        webFragmentNo++;    // webFragmentNoをインクリメント.
        //loadUrl("http://bg1.hatenablog.com");  // loadUrlで"http://bg1.hatenablog.com"をロード.(これだとfragmentManager.findFragmentByTagでみつからず落ちる.)
    }

    // メニュー作成時
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu); // getMenuInflater().inflate()でmenu_mainからメニュー作成.
        menuItemUrlBar = menu.findItem(R.id.menu_item_urlbar);  // menuItemUrlBarを取得.
        menuItemUrlBar.setVisible(true);    // menuItemUrlBar.setVisibleでURLバーのメニューアイテムを表示.
        View view = menuItemUrlBar.getActionView(); // menuItemUrlBar.getActionViewでviewを取得.
        menuUrlBar = (EditText)view.findViewById(R.id.urlBar);   // urlBarを取得.
        menuUrlBar.setOnEditorActionListener(this); // menuUrlBar.setOnEditorActionListenerにthisを指定.
        return true;
    }

    // エディタアクションハンドラ
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            // キーボードの非表示.
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);    // inputMethodManagerを取得.
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);  // inputMethodManager.hideSoftInputFromWindowでソフトウェアキーボードの非表示.
            // URLを取得してロード.
            String url = menuUrlBar.getText().toString();   // menuUrlBar.getText().toString()でURLバーのurlを取得.
            loadUrl(url);   // loadUrlでurlをロード.
            return true;
        }
        return false;
    }

    // URLのロード.
    public void loadUrl(String url){
        // 現在のフラグメントを取得して, そのフラグメントでロードする.
        WebFragment webFragment = (WebFragment)fragmentManager.findFragmentByTag(currentFragmentTag);   // currentFragmentTagでwebFragmentを引く.
        webFragment.loadUrl(url);   // webFragment.loadUrlでurlをロード.
    }

    // menuUrlBarにURLをセット.
    public void setMenuUrlBar(String url){
        menuUrlBar.setText(url);
    }

    // バックキーが押された時.
    @Override
    public void onBackPressed() {
        WebFragment webFragment = (WebFragment)fragmentManager.findFragmentByTag(currentFragmentTag);   // currentFragmentTagでwebFragmentを引く.
        if (!webFragment.goBack()){
           super.onBackPressed();
        }
    }
}