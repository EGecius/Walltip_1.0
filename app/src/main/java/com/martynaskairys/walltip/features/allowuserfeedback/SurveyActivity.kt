package com.martynaskairys.walltip.features.allowuserfeedback

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

import com.martynaskairys.walltip.R

/** Allows user to make suggestions via a survey on how to improve the app  */
class SurveyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        showSurveryInWebView()
    }

    private fun showSurveryInWebView() {
        val myWebView = findViewById(R.id.webview) as WebView
        myWebView.settings.javaScriptEnabled = true
        myWebView.loadUrl("http://bit.ly/2iQ3qXS")
        myWebView.setWebViewClient(WebViewClient())
    }
}
