package com.hyeonjs.dtromap

import android.app.Activity
import android.os.Bundle
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.LinearLayout

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this)
        layout.orientation = 1
        val web = WebView(this)
        val settings: WebSettings = web.getSettings()
        settings.javaScriptEnabled = true
        settings.builtInZoomControls = true
        web.setWebChromeClient(WebChromeClient())
        web.setWebViewClient(WebViewClient())
        web.loadUrl("file:///android_asset/index.html")
        layout.addView(web)
        setContentView(layout)
    }
}
