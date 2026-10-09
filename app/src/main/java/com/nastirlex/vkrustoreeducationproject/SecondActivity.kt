package com.nastirlex.vkrustoreeducationproject

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        applyScreenInsets(findViewById(R.id.second_screen))
        findViewById<TextView>(R.id.received_text).text =
            intent.getStringExtra(EXTRA_TEXT)?.takeIf { it.isNotBlank() }
                ?: getString(R.string.no_received_text)
    }

    companion object {
        const val EXTRA_TEXT = "com.nastirlex.vkrustoreeducationproject.extra.TEXT"
    }
}
