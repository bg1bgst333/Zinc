package com.bgstation0.android.application.zinc;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

/**
 * Created by bg1.
 */
public class DownloadReceiver extends BroadcastReceiver{

    // メンバフィールドの定義.
    public MainActivity activity = null;    // Activityインスタンスactivityをnullにセット.

    // ブロードキャストインテントを受け取った時.
    @Override
    public void onReceive(Context context, Intent intent){
        // アクション文字列ごとに処理を行う.
        String action = intent.getAction(); // intent.getActionでactionを取得.
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){   // ダウンロード完了の時.
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);   // intent.getLongExtraでダウンロードIDを取得.
            DownloadManager.Query query = new DownloadManager.Query();  // DownloadManager.Queryオブジェクトquery作成.
            query.setFilterById(id);    // idでフィルタリング.
            Cursor c = activity.downloadManager.query(query);   // activity.downloadManager.queryにqueryをセットし, Cursorのcを取得.
            if (c.moveToFirst()) {   // カーソルの最初に移動.
                int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS)); // ステータス取得.
                int reason = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_REASON)); // 原因取得.
                // ステータスごとに振り分け.
                if (status == DownloadManager.STATUS_FAILED) {   // ステータスが失敗.
                    Toast.makeText(activity, "STATUS_FAILED", Toast.LENGTH_LONG).show(); // トーストで"STATUS_FAILED"を表示.
                } else if (status == DownloadManager.STATUS_SUCCESSFUL) {  // ステータスが成功.
                    Toast.makeText(activity, "STATUS_SUCCESSFUL", Toast.LENGTH_LONG).show(); // トーストで"STATUS_SUCCESSFUL"を表示.
                }
            }
            c.close();  // c.closeで閉じる.
        }
    }
}
