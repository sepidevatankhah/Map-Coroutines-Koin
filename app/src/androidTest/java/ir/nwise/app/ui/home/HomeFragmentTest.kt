package ir.nwise.app.ui.home

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import ir.nwise.app.R
import ir.nwise.app.ui.MainActivity
import ir.nwise.app.utils.EspressoIdlingResourceRule
import ir.nwise.app.utils.EspressoTestsHelpers
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest
{
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Test
    fun initial_state_home_screen_UI_test() {
        activityTestRule.scenario.moveToState(Lifecycle.State.RESUMED)
        Espresso.onView(ViewMatchers.withId(R.id.spinner))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
        with(Espresso.onView(ViewMatchers.withId(R.id.recyclerView))) {
            check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            check(ViewAssertions.matches(EspressoTestsHelpers.recyclerViewSizeMatcher(28)))
        }
    }

    @Test
    fun scroll_to_eleventh_item_and_click_UI_test() {
        activityTestRule.scenario.moveToState(Lifecycle.State.RESUMED)
        with(Espresso.onView(ViewMatchers.withId(R.id.recyclerView))) {
            perform(RecyclerViewActions.scrollToPosition<CarViewHolder>(10))
            perform(ViewActions.click())
        }
    }

}