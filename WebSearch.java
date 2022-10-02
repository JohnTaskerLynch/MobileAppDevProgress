package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class WebSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_search);

        Intent i = getIntent();
        String webMessage = i.getStringExtra(MainActivity.webMessage);

        WebView webView = new WebView(this);
        setContentView(webView);
        webView.loadUrl("https://www." + webMessage + ".ac.uk/");
    }
}