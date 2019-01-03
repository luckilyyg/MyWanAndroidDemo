package com.crazy.gy.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazy.gy.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends AppCompatActivity {

    @Bind(R.id.content_webview)
    WebView webView;
    @Bind(R.id.img_titleleft)
    ImageView imgTitleleft;
    @Bind(R.id.tv_titlecontent)
    TextView tvTitlecontent;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            link = getIntent().getStringExtra("url");
        }
        initView();
        initData();
    }

    private void initView() {
        imgTitleleft.setVisibility(View.VISIBLE);
        imgTitleleft.setImageResource(R.drawable.ic_left);
        tvTitlecontent.setText("网页");
    }

    private void initData() {
//        webView.loadUrl("file:///android_asset/test.html");//加载asset文件夹下html
        webView.loadUrl(link);//加载url
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        /**
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

    }

    @OnClick(R.id.img_titleleft)
    public void onViewClicked() {
        WebViewActivity.this.finish();
    }
}
