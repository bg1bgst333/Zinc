package com.bgstation0.android.application.zinc;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{ // リストが選択された時のリスナーAdapterView.OnItemClickListenerを実装.

    // メンバフィールドの定義.
    public List<UrlListItem>  bookmarkList = null;  // UrlListItem型ListオブジェクトbookmarkListにnullをセット.
    public ListView lvBookmark = null;  // ListView型lvBookmarkにnullをセット.
    public UrlListAdapter adapter = null;   // UrlListAdapter型adapterにnullをセット.
    public DBHelper hlpr = null;    // DBHelper型hlprにnullをセット.
    public SQLiteDatabase sqlite = null;    // SQLiteDatabase型sqliteにnullをセット.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        // bookmarkListの作成.
        bookmarkList = new ArrayList<UrlListItem>();    // UrlListItem型のArrayListを作成し, bookmarkListに格納.
        // adapterの取得.
        adapter = new UrlListAdapter(this, R.layout.url_list_item, bookmarkList);   // UrlListAdapterの生成.
        // lvBookmarkの取得.
        lvBookmark = (ListView)findViewById(R.id.bookmarklist); // findViewByIdでlvBookmarkを取得.
        // lvBookmarkにadapterをセット.
        lvBookmark.setAdapter(adapter); // lvBookmark.setAdapterでadapterをセット.

        // DBからの読み込み.
        try{
            if (hlpr == null){	// hlprがnullなら.
                hlpr = new DBHelper(getApplicationContext());	// hlprを作成.
            }
            if (sqlite == null){	// sqliteがnullなら.
                sqlite = hlpr.getReadableDatabase();	// 読み込み可能DBを取得.
            }
            Cursor cursor = null;	// Cursorオブジェクトcursorをnull.
            cursor = sqlite.rawQuery("SELECT * FROM bookmark;", null);	// sqlite.rawQueryで"SELECT * FROM bookmark;"を実行.
            int c = cursor.getCount();	// getCountで見つかった個数を取得し, cに格納.
            cursor.moveToFirst();	// cursorを最初の位置に移動.
            for (int i = 0; i < c; i++){	// cの数だけ繰り返し.

                // テーブルから取り出し.
                int _id = cursor.getInt(0);	// 0列目は_id.
                String name = cursor.getString(1);	// 1列目はname.
                String url = cursor.getString(2);	// 2列目はurl.

                // アイテムの追加
                UrlListItem item = new UrlListItem();	// UrlListItemオブジェクトitemを生成.
                item.name = name;	// item.nameにname.
                item.url = url;	// item.urlにurl.
                bookmarkList.add(item);	// bookmarkList.addでitemを追加.

                // 次へ移動.
                cursor.moveToNext();	// cursor.moveToNextで次へ移動.
            }
            cursor.close();	// cursorを閉じる.
        }
        catch (Exception ex){	// 例外キャッチ.
            Log.e("SQLiteOpenHelper_", ex.toString());	// ex.toStringをLogに出力.
        }
        finally{
            sqlite.close();	// sqlite.closeで閉じる.
            sqlite = null;	// null入れておく.
        }

        // ステータス更新.
        int c = bookmarkList.size();    // bookmark数取得.
        if (c > 0){   // 1つ以上
            TextView tv = (TextView) findViewById(R.id.bookmark_status);
            tv.setText(c + "件のブックマーク");
        }
        else{
            TextView tv = (TextView) findViewById(R.id.bookmark_status);
            tv.setText("ブックマークはありません");
        }
        // アダプタの更新.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChangedでUI更新.

        // AdapterView.OnItemClickListenerのセット.
        lvBookmark.setOnItemClickListener(this);    // lvBookmark.setOnItemClickListenerでthis(自分自身)をセット.

    }

    // リストが選択された時.
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        // 選択されたアイテムの取得.
        final ListView lv = (ListView)parent;   // リストビューlv(lvBookmarkと同じ.)を取得.
        final UrlListItem urlListItem = (UrlListItem)lv.getItemAtPosition(position);    // 選択されたアイテムをUrlListItemオブジェクトとして取得.
        Intent data = new Intent(); // dataというIntent作成.
        Bundle bundle = new Bundle();   // Bundleオブジェクトbundle作成.
        bundle.putString("selectedName", urlListItem.name); // bundle.putStringでbundleに"selectedName"というキーでurlListItem.nameを埋め込む.
        bundle.putString("selectedUrl", urlListItem.url);   // bundle.putStringでbundleに"selectedUrl"というキーでurlListItem.urlを埋め込む.
        data.putExtras(bundle); // data.putExtrasでbundleをdataにセット.
        setResult(RESULT_OK, data); // setResultでIntentの結果としてRESULT_OKとdataをセット.
        finish();   // finishでこのアクティビティを終了.(これでMainActivity.onActivityResultに結果とdataが返る.)
    }
}
