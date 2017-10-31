package com.psylife.wrmvplibrary.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by tc on 2017/6/16.
 */

public class MyWebView {

    private WebView mWebView;
    private Context context;
    private WebProgress webProgress;

    private Take take;

    public MyWebView(Context context, WebView mWebView) {
        webProgress = new WebProgress(context);
        this.mWebView = mWebView;
        this.context = context;
    }

    public MyWebView(Context context, WebView mWebView, Take take) {
        webProgress = new WebProgress(context);
        this.mWebView = mWebView;
        this.context = context;
        this.take = take;
    }

    public void webSetting() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        WebSettings settings = mWebView.getSettings();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        if (take != null){
            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onShowFileChooser(WebView webView,
                                                 ValueCallback<Uri[]> filePathCallback,
                                                 FileChooserParams fileChooserParams) {
                    take.take(filePathCallback, null);
                    return true;
                }

                public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                    take.take(null, uploadMsg);
                }

                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                    take.take(null, uploadMsg);
                }

                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                    take.take(null, uploadMsg);
                }

                /**
                 * 获取标题title
                 * @param view
                 * @param title
                 */
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                    take.setTitle(title);
                }

                /**
                 * 进度条
                 * @param view
                 * @param newProgress
                 */
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress == 100) {
                        webProgress.setVisibility(View.GONE);//加载完网页进度条消失
                    } else {
                        webProgress.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                        webProgress.setProgress(newProgress);//设置进度值
                    }

                }
            });
        }

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("url:" + url);
                view.loadUrl(url);
                take.getUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                take.onPageFinished();
            }
        });
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    public interface Take {
        void take(ValueCallback<Uri[]> filePathCallback, ValueCallback<Uri> uploadMsg);

        void setTitle(String title);

        void getUrl(String url);

        void onPageFinished();

    }
}
