package com.example.jokeschucktest.browserfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jokeschucktest.R;

import org.jetbrains.annotations.NotNull;

public class BrowserFragment extends Fragment {

    public final static int TITLE = R.string.browser_title;
    public final static String DEFAULT_URL = "http://www.icndb.com/api/";
    public final static boolean OVERVIEW_MODE = true;
    public final static boolean WIDE_PORT = true;
    public final static boolean BUILT_ZOOM = true;
    public final static boolean DISPLAY_ZOOM = false;
    public final static boolean JS = true;
    public final static String SAVE_TAG = "WebViewState";

    private WebView content;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.browser_fragment, container, false);

        content = layout.findViewById(R.id.browser);
        progressBar = layout.findViewById(R.id.progress_bar);

        getActivity().setTitle(TITLE);

        content.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        content.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView wv, int progress) {
                progressBar.setProgress(progress);
                if (progress == 100)
                    progressBar.setVisibility(View.GONE);
                else
                    progressBar.setVisibility(View.VISIBLE);
            }
        });

        content.setOnKeyListener(new BrowserKeyListener());

        if (savedInstanceState != null) {
            content.restoreState(savedInstanceState.getBundle(SAVE_TAG));
        } else {
            defaultLoad();
        }

        return layout;
    }

    private void defaultLoad() {
        WebSettings settings = content.getSettings();
        settings.setLoadWithOverviewMode(OVERVIEW_MODE);
        settings.setUseWideViewPort(WIDE_PORT);
        settings.setBuiltInZoomControls(BUILT_ZOOM);
        settings.setDisplayZoomControls(DISPLAY_ZOOM);
        settings.setJavaScriptEnabled(JS);
        content.loadUrl(DEFAULT_URL);
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle bundle = new Bundle();
        content.saveState(bundle);
        outState.putBundle(SAVE_TAG, bundle);
    }
}
