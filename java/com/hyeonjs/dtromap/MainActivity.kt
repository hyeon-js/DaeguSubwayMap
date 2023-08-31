package com.hyeonjs.dtromap

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout


class MainActivity : Activity() {

    var web: WebView? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
//            0 ->
//            1 ->
            2 -> startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/hyeon-js/DaeguSubwayMap"))
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(0, 0, 0, "새로 고침")
//            .setIcon()
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        menu.add(0, 1, 0, "앱 정보")
        menu.add(0, 2, 0, "깃허브로 이동")
        return true
    }

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
