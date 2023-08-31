package com.hyeonjs.dtromap

import android.app.Activity
import android.os.Bundle
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.LinearLayout

class MainActivity : Activity() {

    var web: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this)
        layout.orientation = 1
        web = WebView(this)
        val settings: WebSettings = web!!.settings
        settings.javaScriptEnabled = true
        settings.builtInZoomControls = true
        web?.webChromeClient = WebChromeClient()
        web?.webViewClient = WebViewClient()
        web?.loadUrl("file:///android_asset/index.html")
        layout.addView(web)
        setContentView(layout)
    }
}
