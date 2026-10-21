package com.nastirlex.vkrustoreeducationproject

import android.os.ParcelFileDescriptor
import androidx.test.platform.app.InstrumentationRegistry
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
        // Execute individual commands: UiAutomation does not interpret shell operators.
        val directory = "/sdcard/Download/rustore-screenshots"
        shell("mkdir -p $directory")
        shell("screencap -p $directory/$name.png")
        check(shell("ls $directory/$name.png").trim().endsWith("$name.png")) {
            "Screenshot $name was not saved"
        }
    }

    private fun shell(command: String): String {
        val result = InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand(command)
        return ParcelFileDescriptor.AutoCloseInputStream(result).bufferedReader().use { it.readText() }
    }
}
