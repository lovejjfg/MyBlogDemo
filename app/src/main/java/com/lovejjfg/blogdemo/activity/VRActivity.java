package com.lovejjfg.blogdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lovejjfg.blogdemo.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VRActivity extends AppCompatActivity {

    private WebView mWeb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr);
        mWeb = (WebView) findViewById(R.id.wb);
        mWeb.getSettings().setJavaScriptEnabled(true);
        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWeb.post(new Runnable() {
            @Override
            public void run() {
//                mWeb.loadDataWithBaseURL(Constants.BASE_URL, host, Constants.MIME_TYPE, Constants.ENCODING, Constants.FAIL_URL);
                mWeb.loadUrl("http://svpano.oss-cn-hangzhou.aliyuncs.com/test/pinan/20066_20160527090324857/index.html?from=singlemessage&isappinstalled=0");

            }
        });
    }
}
