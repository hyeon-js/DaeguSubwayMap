package com.hyeonjs.dtromap

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.*
import android.widget.LinearLayout
import android.widget.Toast
import com.hyeonjs.library.HttpRequester


class MainActivity : Activity() {

    var web: WebView? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            0 -> updateSubwayInfo();
//            1 ->
            2 -> startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/hyeon-js/DaeguSubwayMap"))
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(0, 0, 0, "열차 위치 새로 고침")
            .setIcon(R.drawable.ic_update)
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
        web?.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
                return super.shouldInterceptRequest(view, url)
            }

            override fun onPageFinished(view: WebView, url: String?) {
                updateSubwayInfo()
                super.onPageFinished(view, url)
            }
        }
        web?.loadUrl("file:///android_asset/index.html")
        layout.addView(web)
        setContentView(layout)
    }

    private fun updateSubwayInfo() {
        Thread{
            try {
                val url = "https://api.hyeonjs.com/dtro";
                val data = HttpRequester.create(url).get()
                if (data == null) {
                    toast("열차 위치 갱신 실패")
                } else {
                    runOnUiThread {
                        web!!.loadUrl("javascript:updateData('$data');")
                        toast("열차 위치를 갱신했어요")
                    }
                }
            }catch (e: Exception) {
                toast(e.toString())
            }
        }.start()
    }

    private fun toast(msg: String) {
        runOnUiThread{
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

}
