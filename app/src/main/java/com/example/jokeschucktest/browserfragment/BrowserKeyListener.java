package com.example.jokeschucktest.browserfragment;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;

public class BrowserKeyListener implements View.OnKeyListener {
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        WebView webView = (WebView) v;
        if (event.getAction() == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }
}
