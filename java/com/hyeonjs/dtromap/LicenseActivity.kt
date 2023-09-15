package com.hyeonjs.dtromap

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader

class LicenseActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this)
        layout.orientation = 1

        val names = arrayOf("대구 도시철도 노선도", "HttpRequester", "Material Design Icon", "나눔스퀘어")
        val licenses = arrayOf("HJS License 1", "HJS License 1", "Apache License 2.0", "SIL OFL 1.1")
        val files = arrayOf("hjs", "HttpRequester", "apache", "font")

        var pad = dip2px(8)
        for (n in names.indices) {
            var name = names[n]
            if (n > 0) name = "\n" + name
            val title = TextView(this)
            title.text = name
            title.textSize = 24f
            layout.addView(title)
            val license = TextView(this)
            license.text = " - ${licenses[n]}"
            license.textSize = 16f
            layout.addView(license)
            val value = TextView(this)
            value.text = readAssets(files[n])
            value.textSize = 18f
            value.setPadding(pad, pad, pad, pad)
            value.setBackgroundColor(Color.parseColor("#EEEEEE"))
            layout.addView(value)
        }

        pad = dip2px(16)
        layout.setPadding(pad, pad, pad, pad)
        val scroll = ScrollView(this)
        scroll.addView(layout)
        setContentView(scroll)
    }

    private fun readAssets(name: String): String {
        val isr = InputStreamReader(assets.open("licenses/${name}.txt"))
        val br = BufferedReader(isr)
        var str: String = br.readLine()
        var line = br.readLine()
        while (line != null) {
            str += "\n$line"
            line = br.readLine()
        }
        isr.close()
        br.close()
        return str
    }

    private fun dip2px(dips: Int) = Math.ceil((dips * resources.displayMetrics.density).toDouble()).toInt()

}