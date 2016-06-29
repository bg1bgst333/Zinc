package com.bgstation0.android.application.zinc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener{	// View.OnClickListener������.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Button1���擾��, OnClickListener�Ƃ��Ď��g���Z�b�g.
        Button button1 = (Button)findViewById(R.id.button1);	// R.id.button1���擾.
        button1.setOnClickListener(this);	// button1.setOnClickListener��this(���g)���Z�b�g.
    }
    
    // View.OnClickListener�C���^�t�F�[�X�̃I�[�o�[���C�h���\�b�h������.
    public void onClick(View v){	// onClick���I�[�o�[���C�h.
    	
    	// �{�^����id�����Ƃɏ�����U�蕪����.
    	switch (v.getId()) {	// v.getId��View(Button)��id���擾.
    	
    		// R.id.button1�̎�.
    		case R.id.button1:
    			
    			// button1�u���b�N
    			{
    				
    				// urlBar��url���擾.
    				EditText urlBar = (EditText)findViewById(R.id.urlbar);	// findViewById��urlBar���擾.
    				String url = urlBar.getText().toString();	// urlBar.getText().toString()��url���擾.
    				
    				// WebView�I�u�W�F�N�gwebView��url�̃T�C�g��\��.
    				WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��webView���擾.
    				webView.loadUrl(url);	// webView.loadUrl��url�̃T�C�g��\��.
    				
    			}
    			
    			// ������.
				break;	// break�Ŕ�����.
				
			// ����ȊO�̎�.
			default:
				
				// ������.
				break;	// break�Ŕ�����.
				
    	}
    	
    }
}