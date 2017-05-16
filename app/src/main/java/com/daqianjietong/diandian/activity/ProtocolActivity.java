package com.daqianjietong.diandian.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.base.BaseActivity;
import com.daqianjietong.diandian.utils.Api;

import butterknife.BindView;
import butterknife.OnClick;


public class ProtocolActivity extends BaseActivity {

    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.protocol_web)
    WebView protocolWeb;

    @Override
    public int getLayoutId() {
        return R.layout.activity_protocol;
    }

    @Override
    public void initView() {
        titleTitle.setText("用户协议");

        protocolWeb.getSettings().setDomStorageEnabled(true);
        protocolWeb.getSettings().setJavaScriptEnabled(true);
        protocolWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        protocolWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        protocolWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        protocolWeb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        protocolWeb.getSettings().setSupportZoom(true);
        protocolWeb.setWebViewClient(new ExampleWebViewClient());
        protocolWeb.loadUrl(Api.HOST+Api.AGREEMENT);
    }


    @OnClick(R.id.title_back)
    public void onClick() {
        finish();
    }



    private class ExampleWebViewClient extends WebViewClient {
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("shouldOverri------", url);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.e("onPageFinished------", url);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.e("onPageStarted------", url);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            Log.e("onLoadResource------", url);
            super.onLoadResource(view, url);
        }


    }

}
