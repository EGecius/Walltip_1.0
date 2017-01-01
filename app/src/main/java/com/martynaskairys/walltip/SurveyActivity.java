package com.martynaskairys.walltip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);

        myWebView.loadUrl("http://bit.ly/2iQ3qXS");

        myWebView.setWebViewClient(new WebViewClient());

    }
}
