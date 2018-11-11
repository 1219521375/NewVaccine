package com.example.pokestar.vaccineremind.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.utils.ToastUtil;

public class WebNewsActivity extends AppCompatActivity {

    WebView mWebView;
    ProgressBar mProgressBar;
    Toolbar toolbar;

    String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_news);

        mWebView = findViewById(R.id.web_news);
        mProgressBar = findViewById(R.id.web_progress);
        toolbar = findViewById(R.id.web_toolbar);

        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        ToastUtil.showShort(getApplicationContext(),mUrl);

//        WebSettings settings = mWebView.getSettings();
//        // 设置支持js脚本
//        settings.setJavaScriptEnabled(true);


        mWebView.setWebViewClient(new WebViewClient() {
            //重写这个方法 返回true，在当前 webView 打开，否则在浏览器中打开
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                ToastUtil.showShort(getApplicationContext(),mUrl);
                view.loadUrl(mUrl);
                return true;
            }


        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                //                LUtils.i("newProgress=" + newProgress);
                if (newProgress != 100) {
                    mProgressBar.setProgress(newProgress);
                } else {
                    mProgressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //网页标题
                toolbar.setTitle(title);

            }
        });

        mWebView.loadUrl(mUrl);

    }
}
