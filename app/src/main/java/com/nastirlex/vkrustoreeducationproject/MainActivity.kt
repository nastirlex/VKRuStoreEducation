package com.nastirlex.vkrustoreeducationproject

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        applyScreenInsets(findViewById(R.id.main_screen))

        val input = findViewById<EditText>(R.id.input_text)
        findViewById<Button>(R.id.open_second_activity).setOnClickListener {
            val text = validatedText(input) ?: return@setOnClickListener
            startActivity(Intent(this, SecondActivity::class.java).apply {
                putExtra(SecondActivity.EXTRA_TEXT, text)
            })
        }
        findViewById<Button>(R.id.dial_friend).setOnClickListener {
            val phone = InputValidator.normalizePhone(input.text.toString())
            if (phone == null) {
                input.error = getString(R.string.invalid_phone)
                input.requestFocus()
                return@setOnClickListener
            }
            input.error = null
            launchExternalIntent(
                Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null)),
                R.string.no_dialer
            )
        }
        findViewById<Button>(R.id.share_text).setOnClickListener {
            val text = validatedText(input) ?: return@setOnClickListener
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            launchExternalIntent(
                Intent.createChooser(sendIntent, getString(R.string.share_chooser_title)),
                R.string.no_share_app
            )
        }
    }

    private fun validatedText(input: EditText): String? {
        val text = input.text.toString()
        if (text.isBlank()) {
            input.error = getString(R.string.empty_text)
            input.requestFocus()
            return null
        }
        input.error = null
        return text
    }

    private fun launchExternalIntent(intent: Intent, errorMessage: Int) {
        try {
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}
