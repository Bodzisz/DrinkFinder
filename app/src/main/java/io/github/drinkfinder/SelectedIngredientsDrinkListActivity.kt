package io.github.drinkfinder

import io.github.drinkfinder.database.DrinkDatabase

class SelectedIngredientsDrinkListActivity : DrinkListActivity() {

    private lateinit var drinksList: List<String>

    @Suppress("UNCHECKED_CAST")
    override fun initDrinksList() {
        val ingredientsNames =
            intent.getCharSequenceArrayListExtra("selectedIngredientsNames") as ArrayList<String>
        drinksList = DrinkDatabase.getInstance(applicationContext).drinkDao()
            .getAllNamesByIngredients(ingredientsNames)
    }

    override fun getDrinksNames(): List<String> {
        return drinksList
    }
}