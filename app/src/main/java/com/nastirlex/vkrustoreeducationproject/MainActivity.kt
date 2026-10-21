package com.nastirlex.vkrustoreeducationproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nastirlex.vkrustoreeducationproject.ui.StoreApp
import com.nastirlex.vkrustoreeducationproject.ui.theme.VKRuStoreEducationProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { VKRuStoreEducationProjectTheme { StoreApp() } }
    }
}
