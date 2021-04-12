package org.techtown.led;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class CctvPage extends AppCompatActivity {

    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv_page);

        webView = (WebView) findViewById(R.id.cctv);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        String url ="http://172.30.1.28:4998/?action=stream";
        webView.loadUrl(url);





        Button button2 =(Button) findViewById(R.id.button_back);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cctv-> getdatabase page로 전달
                Intent intent = new Intent();
                intent.putExtra("status","ok");
                setResult(Activity.RESULT_OK, intent);

                finish();

            }
        });
    }
}