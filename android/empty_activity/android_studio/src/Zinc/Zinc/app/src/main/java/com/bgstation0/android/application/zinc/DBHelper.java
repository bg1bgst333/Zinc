package com.bgstation0.android.application.zinc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by bg1.
 */
public class DBHelper extends SQLiteOpenHelper {

    // メンバフィールドの定義.
    private static final String DB = "zinc1.db";    // DB名は"zinc1.db".
    private static final int DB_VERSION = 1;        // DBバージョンは1
    private static final String TABLE_BOOKMARK = "bookmark";    // bookmarkテーブル.
    private static final String TABLE_HISTORY = "history";  // historyテーブル.
    private static final String CREATE_TABLE_BOOKMARK = "create table " + TABLE_BOOKMARK + " ( _id integer primary key autoincrement, name string, url string);";	// BOOKMARKのCREATE_TABLE文.
    private static final String CREATE_TABLE_HISTORY = "create table " + TABLE_HISTORY + " ( _id integer primary key autoincrement, name string, url string);";      // HISTORYのCREATE_TABLE文.
    private static final String DROP_TABLE_BOOKMARK = "drop table " + TABLE_BOOKMARK + ";"; // BOOKMARKのDROP_TABLE文.
    private static final String DROP_TABLE_HISTORY = "drop table " + TABLE_HISTORY + ";";   // HISTORYのDROP_TABLE文.

    // コンストラクタ
    public DBHelper(Context context){
        super(context, DB, null, DB_VERSION);   // 親クラスコンストラクタに任せる.
    }

    // メンバメソッドの定義.
    // DB作成時.
    public void onCreate(SQLiteDatabase db){
        // テーブル作成の実行.
        try{	// tryで囲む.
            db.execSQL(CREATE_TABLE_BOOKMARK);	 // db.execSQLでCREATE_TABLE_BOOKMARKを実行.
            db.execSQL(CREATE_TABLE_HISTORY);  // db.execSQLでCREATE_TABLE_HISTORYを実行.
        }
        catch(Exception ex){	// 例外キャッチ.
            Log.e("Zinc", ex.toString());	// ex.toStringをLogに出力.
        }
    }

    // DBバージョンアップグレード時.
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // テーブル再作成.
        db.execSQL(DROP_TABLE_BOOKMARK );	// いったんドロップ.
        db.execSQL(DROP_TABLE_HISTORY);   // いったんドロップ.
        onCreate(db);	// 再作成.
    }
}
