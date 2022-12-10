package io.github.drinkfinder

import io.github.drinkfinder.database.DrinkDatabase

class SelectedIngredientsDrinkListActivity : DrinkListActivity() {

    @Suppress("UNCHECKED_CAST")
    override fun initDrinksList() {
        val ingredientsNames = intent.getCharSequenceArrayListExtra("selectedIngredientsNames") as ArrayList<String>
        drinksList = DrinkDatabase.getInstance(applicationContext).drinkDao().getAllByIngredients(ingredientsNames)
    }

    override fun getDrinksNames() : List<String> {
        val result = HashSet<String>()
        for(drink in drinksList) {
            result.add(drink.name)
        }
        return result.toList()
    }
}