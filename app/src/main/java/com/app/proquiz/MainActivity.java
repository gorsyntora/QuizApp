package com.app.proquiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.*;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.content.ContentValues.TAG;
import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
import static com.app.proquiz.SysCheck.checkIsEmu;


public class MainActivity extends AppCompatActivity {

    private static final String APP_LINK = "APP_Link";
    private static final String APP_PREFERENCES = "APP_PREFERENCES";
    WebView webView;
    private String startUrl;
    private SharedPreferences appSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow()
                .setFlags(WindowManager.LayoutParams
                        .FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.adWebView);

     WebBuilder.build(webView);

         initApp();
    }


    void initApp() {

        appSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);


            if (appSettings.contains(APP_LINK)) {
                if (!isConnected()) {
                    View v = findViewById(R.id.message_layout);
                    v.setVisibility(View.VISIBLE);
                }
              else
                webView.loadUrl(appSettings.getString(APP_LINK, null));
            } else if
            (checkIsEmu())
                loadAppActivity();
            else
                getFirebaseData();
        }


    void loadAppActivity() {
        Intent intent = new Intent(getApplicationContext(), ProQuizActivity.class);
        startActivity(intent);
    }

    void getFirebaseData() {
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            startUrl = mFirebaseRemoteConfig.getString("startURL").replace("\"", "");
                            Log.d("REMOTE", "Config params updated: " + startUrl);
                            if (!startUrl.isEmpty()) {
                                saveLink(startUrl);
                                webView.loadUrl(startUrl);
                            } else loadAppActivity();

                        } else {
                            getFirebaseData();
                        }
                    }
                });
    }

    private void saveLink(String startUrl) {
        SharedPreferences.Editor editor = appSettings.edit();
        editor.putString(APP_LINK, startUrl);
        editor.apply();

    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
    }

    public void onRestart(View view) {
        View v = findViewById(R.id.message_layout);
        v.setVisibility(View.INVISIBLE);
        initApp();
    }

    static class WebBuilder {
        @SuppressLint("SetJavaScriptEnabled")
        static void build(WebView webView) {

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setDatabaseEnabled(true);
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            webSettings.setAllowContentAccess(true);
            //    webSettings.setAllowFileAccess(true);
            webSettings.setSupportMultipleWindows(false);
            webSettings.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);


            webView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                            webView.goBack();
                            return true;
                        }
                    }
                    return false;
                }
            });
            webView.setWebChromeClient(new WebChromeClient() {
            });
            webView.setWebViewClient(new WebViewClient() {});
           /* webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();
                }
            });*/

        }
    }
}