package com.example.empmgmnt;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutCompanyActivity extends AppCompatActivity {
    WebView wbvcompany;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_company);
        wbvcompany=findViewById(R.id.companyWBV);
        WebSettings webSettings=wbvcompany.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wbvcompany.loadUrl("https://www.facebook.com/");
    }

}