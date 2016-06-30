package com.bgstation0.android.application.zinc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bg1.
 */
public class UrlListAdapter extends ArrayAdapter<UrlListItem> {

    // インフレータの定義.
    private LayoutInflater inflater;    // インフレータ

    // コンストラクタ
    public UrlListAdapter(Context context, int resource, List<UrlListItem> objects){
        super(context, resource, objects);
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);	// インフレーターの取得.
    }

    // アイテム表示のカスタマイズ
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // convertViewがnullの時.
        if (convertView == null){
            // リソースから作成.
            convertView = inflater.inflate(R.layout.url_list_item, null);   // convertViewがnullなら, inflater.inflateで取得.
        }
        // 各々のビューにアイテムの各項目をセット.
        TextView tvName = (TextView)convertView.findViewById(R.id.url_list_item_name);  // tvNameを取得.
        tvName.setText(getItem(position).name);  // tvName.setTextでgetItem(position).nameをセット.
        TextView tvUrl = (TextView)convertView.findViewById(R.id.url_list_item_url);   // tvUrlを取得.
        tvUrl.setText(getItem(position).url);   // tvUrl.setTextでgetitem(position).urlをセット.
        return convertView; // convertViewを返す.
    }

}
