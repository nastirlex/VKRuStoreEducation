package com.nastirlex.vkrustoreeducationproject

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

internal fun applyScreenInsets(view: View) {
    val left = view.paddingLeft
    val top = view.paddingTop
    val right = view.paddingRight
    val bottom = view.paddingBottom
    ViewCompat.setOnApplyWindowInsetsListener(view) { screen, windowInsets ->
        val insets = windowInsets.getInsets(
            WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
        )
        screen.setPadding(
            left + insets.left,
            top + insets.top,
            right + insets.right,
            bottom + insets.bottom
        )
        windowInsets
    }
    ViewCompat.requestApplyInsets(view)
}
