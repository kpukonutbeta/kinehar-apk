package com.kpukonutbeta.kinehar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        myWebView = findViewById(R.id.webview);

        // --- WebView Settings ---
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAllowFileAccess(true);

        // Stay inside the app when clicking links
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Create an Intent to view the URL in an external application
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                // Add a category to ensure it opens in a browsable app
                browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);

                // Launch the external application (the default browser)
                try {
                    startActivity(browserIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Handle the case where no app can handle the intent
                    // e.g., show a Toast message
                }

                // Return true to indicate the host application handles the URL,
                // preventing the WebView from loading it internally
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        // --- Load Your Website ---
        myWebView.loadUrl("https://kpukonutbeta.github.io/kinehar/");

        // --- Modern Back Gesture Handling ---
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (myWebView.canGoBack()) {
                    myWebView.goBack(); // Go back one page in web history
                } else {
                    setEnabled(false); // Disable this callback
                    getOnBackPressedDispatcher().onBackPressed(); // Exit app
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}