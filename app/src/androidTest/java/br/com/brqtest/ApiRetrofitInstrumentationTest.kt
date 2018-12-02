package br.com.brqtest

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import br.com.brqtest.viewkotlin.ClienteDetailActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ApiRetrofitInstrumentationTest {
    @Rule
    @JvmField
    val rule = IntentsTestRule(ClienteDetailActivity::class.java)

    @Test
    fun useAppContext() {
        onView(withId(R.id.inpCEP))
            .perform(typeText("08773-130"))

        onView(withId(R.id.inpLogradouro))
            .perform(click())


    }
}