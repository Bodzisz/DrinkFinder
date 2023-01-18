package io.github.drinkfinder.ui

import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.github.drinkfinder.*
import io.github.drinkfinder.ingredientList.ListIngredientDataModel
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    @get : Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @Test
    fun actionBarTitle_shouldEqualToDrinkFinder() {
        onView(withId(R.id.toolbarTitle)).check(matches(withText(R.string.app_name)))
    }

    @Test
    fun navMenu_shouldOpenAndClose_whenToggleIsClicked() {
        onView(withId(R.id.main_layout))
            .check(matches(isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())
            .check(matches(isOpen()))
            .perform(DrawerActions.close())
            .check(matches(isClosed()))
    }

    @Test
    fun navMenu_shouldStartFavouriteDrinksActivity_whenFavouriteDrinksItemClicked() {
        onView(withId(R.id.main_layout))
            .check(matches(isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())

        onView(withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.favourite_drinks))

        intended(hasComponent(FavouriteDrinkListActivity::class.java.name))
    }

    @Test
    fun navMenu_shouldChangeAllIngredientsOption_whenItemSwitchClicked() {
        onView(withId(R.id.main_layout))
            .check(matches(isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())

        val prefs = InstrumentationRegistry.getInstrumentation().targetContext.getSharedPreferences(
            "Preferences", AppCompatActivity.MODE_PRIVATE)
        val beforeVal = prefs.getBoolean("allIngredients", false)

        onView(withId(R.id.switch_id))
            .perform(click())

        val afterVal = prefs.getBoolean("allIngredients", false)
        assertTrue(beforeVal != afterVal)
    }

    @Test
    fun searchButton_shouldStartSelectedDrinkListActivityWithSelectedIngredients_whenClicked() {
        onView(withId(R.id.clear_button))
            .perform(click())

        val limeVodka = "Lime vodka"
        val peach = "Peach"
        val ale = "Ale"


        onData(withIngredientName(limeVodka))
            .perform(click())

        onData(withIngredientName(peach))
            .perform(click())

        onData(withIngredientName(ale))
            .perform(click())

        onView(withId(R.id.search_button)).perform(click())

        intended(hasComponent(SelectedIngredientsDrinkListActivity::class.java.name))
        intended(hasExtra(equalTo("selectedIngredientsNames"), allOf(
            hasItem(equalTo(limeVodka)),
            hasItem(equalTo(peach)),
            hasItem(equalTo(ale))
        )))
    }

    @Test
    fun ingredientKeyboardSearch_shouldFilterValuesInAlphabeticalOrder_whenBeginningOfIngredientNameEntered() {
        onView(withId(R.id.clear_button))
            .perform(click())

        onView(withId(R.id.ingredientSearch))
            .perform(click())
            .perform(typeText("absol"))

        onData(anything())
            .inAdapterView(withId(R.id.listview_1))
            .atPosition(0)
            .onChildView(withId(R.id.ingredientName))
            .check(matches(withText("Absolut Kurant")))

        onData(anything())
            .inAdapterView(withId(R.id.listview_1))
            .atPosition(1)
            .onChildView(withId(R.id.ingredientName))
            .check(matches(withText("Absolut Peppar")))

        onData(anything())
            .inAdapterView(withId(R.id.listview_1))
            .atPosition(2)
            .onChildView(withId(R.id.ingredientName))
            .check(matches(withText("Absolut Vodka")))

        onData(anything())
            .inAdapterView(withId(R.id.listview_1))
            .atPosition(3)
            .onChildView(withId(R.id.ingredientName))
            .check(matches(withText("Absolut citron")))
    }

    @Test
    fun showSelectedIngredients_showsSelectedIngredients_whenShowSelectedIngredientsOptionInOverflowMenuClicked() {
        onView(withId(R.id.clear_button))
            .perform(click())

        val limeVodka = "Lime vodka"
        val peach = "Peach"
        val ale = "Ale"

        onData(withIngredientName(limeVodka))
            .perform(click())

        onData(withIngredientName(peach))
            .perform(click())

        onData(withIngredientName(ale))
            .perform(click())


        onView(withId(R.id.overflowMenu))
            .perform(click())

        onView(withText(R.string.show_selected_ingredients_option))
            .perform(click())

        intended(hasComponent(ShowSelectedIngredientsActivity::class.java.name))
        intended(hasExtra(equalTo("selectedIngredientsNames"), allOf(
            hasItem(equalTo(limeVodka)),
            hasItem(equalTo(peach)),
            hasItem(equalTo(ale))
        )))

    }

    @Test
    fun clearButton_unchecksItem_whenItIsSelected() {
        val apple = "Apple"

        onData(withIngredientName(apple))
            .perform(click())

        onData(withIngredientName(apple))
            .onChildView(withId(R.id.checkbox))
            .check(matches(isChecked()))

        onView(withId(R.id.clear_button))
            .perform(click())

        onData(withIngredientName(apple))
            .onChildView(withId(R.id.checkbox))
            .check(matches(not(isChecked())))
    }

    @After
    fun clean() {
        Intents.release()
    }

    private fun withIngredientName(name: String): Matcher<ListIngredientDataModel> {
        return object : TypeSafeMatcher<ListIngredientDataModel>() {
            public override fun matchesSafely(item: ListIngredientDataModel?): Boolean {
                if (item != null) {
                    return item.name.equals(name)
                }
                return false
            }

            override fun describeTo(description: Description?) {
            }
        }
    }
}