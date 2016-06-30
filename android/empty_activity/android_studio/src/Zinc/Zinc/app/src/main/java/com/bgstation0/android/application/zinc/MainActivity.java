package com.bgstation0.android.application.zinc;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{	// View.OnClickListenerを実装.

    // メンバフィールドの定義.
    public DBHelper hlpr = null;    // DBHelper型hlprにnullをセット.
    public SQLiteDatabase sqlite = null;    // SQLiteDatabase型sqliteにnullをセット.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // WebViewClientのセット.
        WebView webView = (WebView)findViewById(R.id.webview);  // webViewを取得.
        webView.setWebViewClient(new CustomWebViewClient());    // setWebViewClientでCustomWebViewClientのインスタンスをセット.(これをやらないと一部のサイトでChromeにリダイレクトしてしまう.)

        // Button1を取得し, OnClickListenerとして自身をセット.
        Button button1 = (Button)findViewById(R.id.button1);	// R.id.button1を取得.
        button1.setOnClickListener(this);	// button1.setOnClickListenerでthis(自身)をセット.
    }

    // View.OnClickListenerインタフェースのオーバーライドメソッドを実装.
    public void onClick(View v){	// onClickをオーバーライド.

        // ボタンのidをごとに処理を振り分ける.
        switch (v.getId()) {	// v.getIdでView(Button)のidを取得.

            // R.id.button1の時.
            case R.id.button1:

                // button1ブロック
                {

                    // urlBarのurlを取得.
                    EditText urlBar = (EditText)findViewById(R.id.urlbar);	// findViewByIdでurlBarを取得.
                    String url = urlBar.getText().toString();	// urlBar.getText().toString()でurlを取得.

                    // WebViewオブジェクトwebViewでurlのサイトを表示.
                    WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでwebViewを取得.
                    webView.loadUrl(url);	// webView.loadUrlでurlのサイトを表示.

                }

                // 抜ける.
                break;	// breakで抜ける.

            // それ以外の時.
            default:

                // 抜ける.
                break;	// breakで抜ける.

        }

    }

    // メニュー作成時
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // リソースからメニュー作成.
        getMenuInflater().inflate(R.menu.menu_main, menu); // getMenuInflater().inflate()でmenu_mainからメニュー作成.
        return true;    // trueを返す.
    }

    // メニュー選択時
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        // 選択されたアイテムごとに振り分ける.
        int id = item.getItemId();  // item.getItemIdで選択されたidを取得.
        if (id == R.id.action_bookmark_manager){    // "ブックマークの管理"

            // IntentでBookmarkActivityを起動.
            String pkgName = getPackageName();  // getPackageNameでパッケージ名取得.
            Intent intent = new Intent();   // intent作成.
            intent.setClassName(pkgName, pkgName + ".BookmarkActivity");    // intent.setClassNameでBookmarkActivityのパッケージ名を指定.
            startActivity(intent);  // startActivityでアクティビティ起動.

        }
        else if (id == R.id.action_bookmark_add){

            // URL編集ダイアログを表示.
            LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);    // getSystemServiceでinflaterサービスを取得.
            final View layout = inflater.inflate(R.layout.url_edit_dialog, null);   // inflateでリソースR.layout.dialogからダイアログのViewを作成.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);	// AlertDialog.Builderの作成.
            WebView webView = (WebView)findViewById(R.id.webview);  // webViewを取得.
            final String name = webView.getTitle();   // webView.getTitleでページタイトル取得.
            final String url = webView.getUrl();      // webView.getUrlでページURL取得.
            builder.setTitle("ブックマークの登録");	// setTitleでタイトルに"ブックマークの登録"をセット.
            EditText editTextName = (EditText)layout.findViewById(R.id.edit_text_name); // editTextName取得.
            EditText editTextUrl = (EditText)layout.findViewById(R.id.edit_text_url);  // editTextUrl取得.
            editTextName.setText(name);
            editTextUrl.setText(url);
            builder.setView(layout);	// setViewでビューにlayoutをセット.
            builder.setPositiveButton("登録", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    // DB登録処理
                    EditText registName = (EditText)layout.findViewById(R.id.edit_text_name);   // OK押した後のedit_text_name取得.
                    EditText registUrl = (EditText)layout.findViewById(R.id.edit_text_url);     // OK押した後のedit_text_url取得.
                    String strName = registName.getText().toString(); // registName.getTextの文字列取得.
                    String strUrl = registUrl.getText().toString();   // registUrl.getTextの文字列取得.
                    long id = -1;   // insertの戻り値を格納するidに-1をセット.
                    try{
                        if (hlpr == null){  // hlprがnullなら.
                            hlpr = new DBHelper(getApplicationContext());   // DBHelperを生成.
                        }
                        if (sqlite == null) {    // sqliteがnullなら.
                            sqlite = hlpr.getWritableDatabase();    // hlpr.getWritableDatabaseでDBの書き込みが可能に.
                        }
                        ContentValues values = new ContentValues(); // テーブルに挿入する値の箱ContentValuesを用意.
                        values.put("name", strName);    // nameとstrNameのキーをput.
                        values.put("url", strUrl);      // urlとstrUrlのキーをput.
                        id = sqlite.insertOrThrow("bookmark", null, values);    // sqlite.insertOrThrowでinsert.
                    }
                    catch (Exception ex){
                        Log.d("Zinc", ex.toString());
                    }
                    finally {
                        sqlite.close();
                        sqlite = null;
                        hlpr.close();
                        hlpr = null;
                    }

                }
            });
            builder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // キャンセル処理(実質何もしない.).
                }
            });
            builder.create().show();    // ダイアログの作成と表示.
        }
        return super.onOptionsItemSelected(item);
    }

}