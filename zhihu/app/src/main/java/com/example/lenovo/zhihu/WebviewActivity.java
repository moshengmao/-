package com.example.lenovo.zhihu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.lenovo.zhihu.Bean.HotNews;

import java.util.List;

public class WebviewActivity extends AppCompatActivity {
private List<HotNews>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        WebView webView=(WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://news.at.zhihu.com/css/news_qa.auto.css?v=1edab");//接口7，栏目具体消息
    }
}
