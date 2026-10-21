package com.nastirlex.vkrustoreeducationproject

import android.graphics.Bitmap
import android.os.ParcelFileDescriptor
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class StoreNavigationTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()

    @Test fun listOpensSelectedPracticeCardAndToolbarReturnsToList() {
        screenshot("catalog")
        compose.onNodeWithTag("app-music").performClick()
        compose.onNodeWithTag("app-details").assertIsDisplayed()
        compose.onNodeWithTag("app-details-title").assertTextEquals("Музыка").assertIsDisplayed()
        screenshot("details")
        compose.onNodeWithContentDescription("Назад").performClick()
        compose.onNodeWithTag("app-list").assertIsDisplayed()
    }

    @Test fun allThreeShowcasePagesReturnToExistingCatalog() {
        val firstApps = listOf("vk", "messenger", "learn")
        firstApps.forEachIndexed { page, id ->
            compose.onNodeWithTag("app-vk").performClick()
            compose.onNodeWithTag("showcase-pager").performScrollTo()
            repeat(page) { compose.onNodeWithTag("showcase-pager").performTouchInput { swipeLeft() } }
            compose.onNodeWithText("${page + 1} / 3").assertIsDisplayed()
            screenshot("showcase-${page + 1}")
            compose.onNodeWithTag("app-$id").assertIsDisplayed().performClick()
            compose.onNodeWithTag("app-list").assertIsDisplayed()
        }
    }

    @Test fun detailsAndShowcasePageSurviveActivityRecreation() {
        compose.onNodeWithTag("app-mail").performClick()
        compose.onNodeWithTag("showcase-pager").performScrollTo()
        compose.onNodeWithTag("showcase-pager").performTouchInput { swipeLeft() }
        compose.onNodeWithText("2 / 3").assertIsDisplayed()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithTag("app-details").assertIsDisplayed()
        compose.onNodeWithText("2 / 3").assertIsDisplayed()
        compose.onNodeWithTag("app-messenger").performClick()
        compose.onNodeWithTag("app-list").assertIsDisplayed()
    }
    private fun screenshot(name: String) {
        compose.waitForIdle()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val directory = File(context.getExternalFilesDir(null), "screenshots").apply { mkdirs() }
        val file = File(directory, "$name.png")
        file.outputStream().use { output ->
            compose.onRoot().captureToImage().asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, output)
        }
        // UTP uninstalls the app after the run; keep CI images outside its data directory.
        val command = "mkdir -p /sdcard/Download/rustore-screenshots && " +
            "cp ${file.absolutePath} /sdcard/Download/rustore-screenshots/$name.png"
        val result = InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand(command)
        ParcelFileDescriptor.AutoCloseInputStream(result).use { it.readBytes() }
    }

}
