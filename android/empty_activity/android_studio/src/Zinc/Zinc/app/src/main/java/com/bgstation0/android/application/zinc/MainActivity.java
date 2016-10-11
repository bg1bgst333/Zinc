package com.bgstation0.android.application.zinc;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // メンバフィールドの定義
    private FragmentManager fragmentManager = null; // FragmentManager型fragmentManagerをnullにセット.
    private final String FRAGMENT_TAB_PREFIX_WEB = "web";   // 定数FRAGMENT_TAG_PREFIX_WEBを"web"とする.
    private int webFragmentNo = 0;  // int型webFragmentNoを0にセット.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 最初のWebフラグメントの追加.
        fragmentManager = getSupportFragmentManager();  // getSupportFragmentManagerでfragmentManagerを取得.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();   // fragmentManager.beginTransactionでfragmentTransaction取得.
        String fragmentTag = FRAGMENT_TAB_PREFIX_WEB + webFragmentNo;   // fragmentTagはFRAGMENT_TAG_PREFIX_WEBにwebFragmentNoを付けたものとする.
        WebFragment webFragment = new WebFragment();    // WebFragmentオブジェクトwebFragmentの生成.
        fragmentTransaction.add(R.id.content, webFragment, fragmentTag);    // fragmentTransaction.addでwebFragmentをfragmentTagを付けてR.id.contentに追加.
        fragmentTransaction.commit();   // fragmentTransaction.commitで確定.
        webFragmentNo++;    // webFragmentNoをインクリメント.
    }

}