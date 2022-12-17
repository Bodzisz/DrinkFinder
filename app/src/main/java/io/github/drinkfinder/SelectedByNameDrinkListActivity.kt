package io.github.drinkfinder

class SelectedByNameDrinkListActivity : DrinkListActivity() {

    private lateinit var drinksList: List<String>

    override fun initDrinksList() {
        drinksList = listOf()
    }

    override fun getDrinksNames(): List<String> {
        return drinksList
    }
}