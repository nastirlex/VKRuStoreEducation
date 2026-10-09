package com.nastirlex.vkrustoreeducationproject

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.matcher.IntentMatchers.hasType
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntentActivityTest {
    @Before
    fun setUp() = Intents.init()

    @After
    fun tearDown() = Intents.release()

    @Test
    fun explicitIntentDisplaysOriginalTextAndSurvivesRecreation() {
        ActivityScenario.launch(MainActivity::class.java).use {
            val text = "  Привет!\nВторая строка  "
            enterText(text)
            onView(withId(R.id.open_second_activity)).perform(click())
            intended(allOf(hasComponent(SecondActivity::class.java.name),
                hasExtra(SecondActivity.EXTRA_TEXT, text)))
            onView(withId(R.id.received_text)).check(matches(withText(text)))
            pressBack()
        }
        ActivityScenario.launch<SecondActivity>(
            Intent(androidx.test.platform.app.InstrumentationRegistry
                .getInstrumentation().targetContext, SecondActivity::class.java)
                .putExtra(SecondActivity.EXTRA_TEXT, "После поворота")
        ).use { scenario ->
            scenario.recreate()
            onView(withId(R.id.received_text)).check(matches(withText("После поворота")))
        }
    }

    @Test
    fun dialIntentContainsNormalizedPhone() {
        intending(hasAction(Intent.ACTION_DIAL)).respondWith(ActivityResult(Activity.RESULT_OK, null))
        ActivityScenario.launch(MainActivity::class.java).use {
            enterText("+7 (999) 123-45-67")
            onView(withId(R.id.dial_friend)).perform(click())
            intended(allOf(hasAction(Intent.ACTION_DIAL), hasData("tel:+79991234567")))
        }
    }

    @Test
    fun shareUsesSystemChooserWithPlainText() {
        intending(hasAction(Intent.ACTION_CHOOSER)).respondWith(ActivityResult(Activity.RESULT_OK, null))
        ActivityScenario.launch(MainActivity::class.java).use {
            val text = "Текст для друга"
            enterText(text)
            onView(withId(R.id.share_text)).perform(click())
            intended(allOf(hasAction(Intent.ACTION_CHOOSER), hasExtra(
                `is`(Intent.EXTRA_INTENT), allOf(hasAction(Intent.ACTION_SEND),
                    hasType("text/plain"), hasExtra(Intent.EXTRA_TEXT, text)))))
        }
    }

    @Test
    fun blankInputDoesNotLaunchAnyIntent() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            enterText("   ")
            val initialIntents = Intents.getIntents().size
            listOf(R.id.open_second_activity, R.id.share_text, R.id.dial_friend).forEach { button ->
                onView(withId(button)).perform(click())
                scenario.onActivity { activity ->
                    assertTrue(activity.findViewById<EditText>(R.id.input_text).error.isNotBlank())
                }
            }
            assertEquals(initialIntents, Intents.getIntents().size)
        }
    }

    @Test
    fun invalidPhoneDoesNotLaunchDialerAndInputSurvivesRecreation() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            enterText("не номер")
            val initialIntents = Intents.getIntents().size
            onView(withId(R.id.dial_friend)).perform(click())
            scenario.onActivity { activity ->
                assertEquals(activity.getString(R.string.invalid_phone),
                    activity.findViewById<EditText>(R.id.input_text).error.toString())
            }
            assertEquals(initialIntents, Intents.getIntents().size)
            scenario.recreate()
            onView(withId(R.id.input_text)).check(matches(withText("не номер")))
        }
    }

    @Test
    fun missingExtraShowsFallback() {
        ActivityScenario.launch(SecondActivity::class.java).use {
            onView(withId(R.id.received_text)).check(matches(withText(R.string.no_received_text)))
        }
    }

    private fun enterText(text: String) {
        onView(withId(R.id.input_text)).perform(replaceText(text), closeSoftKeyboard())
    }
}
