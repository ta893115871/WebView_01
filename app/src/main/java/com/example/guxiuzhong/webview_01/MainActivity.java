package com.example.guxiuzhong.webview_01;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.id_tv)
    TextView textView;
    @Bind(R.id.webView_id)
     WebView webView;
    @Bind(R.id.id_tv_error)
     TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        webView.loadUrl("http://www.baidu.com");
        webView.loadUrl("http://zhushou.360.cn");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //获取URL的title
                textView.setText(title);
                super.onReceivedTitle(view, title);
            }

        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                System.out.println("-----old--onReceivedError--------");
                //方法1 加载本地错误页面H5
//                view.loadUrl("file:///android_asset/error.html");

                //方法2 本地化加载错误页面-//这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。

                errorTextView.setVisibility(View.VISIBLE);
                errorTextView.setText(" 404 error ");
                view.setVisibility(View.GONE);
            }

            //最新的好像不调用
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                System.out.println("-----new --onReceivedError--------");
                //方法1 加载本地错误页面H5
//                view.loadUrl("file:///android_asset/error.html");

                //方法2 本地化加载错误页面-//这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。

                errorTextView.setVisibility(View.VISIBLE);
                errorTextView.setText(" 404 error ");
                view.setVisibility(View.GONE);

            }
        });

        webView.setDownloadListener(new DownListener());
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickReFresh(View view) {
        webView.reload();
    }

    private class DownListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            System.out.println("------url>>>" + url);
            if (url.endsWith(".apk")) {
                //方法1 自己处理webview下载文件
                new HttpThread(url).start();

                //方法2 使用系统的浏览器下载文件
//                Uri uri = Uri.parse(url);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
