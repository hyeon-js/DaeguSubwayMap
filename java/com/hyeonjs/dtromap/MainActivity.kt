package com.hyeonjs.dtromap

import android.app.Activity
import android.app.AlertDialog
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

    val VERSION = "1.0";
    var web: WebView? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            0 -> updateSubwayInfo();
            1 -> showDialog("앱 정보", "이름 : 대구 도시철도 노선도\n버전 : ${VERSION}\n개발자 : HyeonJS\n\n" +
                    "  대구 도시철도의 노선도를 보여주는 앱이에요. 시간표 기반 운행 정보도 확인할 수 있어요.\n\n" +
                    "  살짝 반투명한 검은색 아이콘이 있는 곳이 시간표상 열차가 있어야 할 곳이지만, 이 앱은 앱에서 제공하는 정보에 대한 보증을 하지 않아요.\n\n" +
                    "  이 앱은 개인이 개발한 것으로 도시철도 운영 기관과는 관련이 없어요. 이 앱에서 제공하는 정보와 관련하여 해당 운영기관에 민원을 넣지 말아주세요.");
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
        settings.allowFileAccessFromFileURLs = true;
        settings.allowUniversalAccessFromFileURLs = true;
//        web?.webChromeClient = WebChromeClient()

        web?.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                toast(consoleMessage.message())
                return false
            }
        }
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

    private fun showDialog(title: String, msg: String) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(title)
        dialog.setMessage(msg)
        dialog.setNegativeButton("닫기", null)
        dialog.show()
    }

    private fun toast(msg: String) {
        runOnUiThread{
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

}
