package net.telepathix.petbase.tests

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility.*
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import net.telepathix.petbase.PetBaseTestApp
import net.telepathix.petbase.R
import net.telepathix.petbase.model.Config
import net.telepathix.petbase.ui.home.HomeActivity
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test currently crash on Android 9 due to a bug in an internal library
 */
@RunWith(AndroidJUnit4::class)
class BothTest {

    @get:Rule
    var activityRule: ActivityTestRule<HomeActivity>
            = ActivityTestRule(HomeActivity::class.java)

    @Before
    fun updateConfig() {
        ApplicationProvider.getApplicationContext<PetBaseTestApp>().config =
            Config(true, true, "M-F 8:00-16:00")
        activityRule.activity.updateConfig()
    }

    @Test
    fun checkButtonVisibility() {
        onView(withId(R.id.chatButton)).check(matches(withEffectiveVisibility(VISIBLE)))
        onView(withId(R.id.callButton)).check(matches(withEffectiveVisibility(VISIBLE)))
    }
}
