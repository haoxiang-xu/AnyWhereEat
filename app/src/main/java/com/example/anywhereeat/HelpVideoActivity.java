package com.example.anywhereeat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class HelpVideoActivity extends AppCompatActivity {
    WebView googlePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_video);


        googlePage = (WebView) findViewById(R.id.googlePageView);
        googlePage.loadUrl("https://youtu.be/r-3LhSF_x10");
    }
}