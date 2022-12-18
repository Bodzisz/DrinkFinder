package io.github.drinkfinder

import io.github.drinkfinder.database.DrinkDatabase

class SelectedIngredientsDrinkListActivity : DrinkListActivity() {

    private lateinit var drinksList: List<String>

    @Suppress("UNCHECKED_CAST")
    override fun initDrinksList() {
        val ingredientsNames =
            intent.getCharSequenceArrayListExtra("selectedIngredientsNames") as ArrayList<String>
        val preferences = getSharedPreferences("Preferences", MODE_PRIVATE)
        val allIngredients = preferences.getBoolean("allIngredients", false)
        drinksList = if(allIngredients) {
            getDrinkNamesByAllIngredients(ingredientsNames)
        } else {
            DrinkDatabase.getInstance(applicationContext).drinkDao()
                .getAllNamesByIngredients(ingredientsNames)
        }
    }

    override fun getDrinksNames(): List<String> {
        return drinksList
    }

    private fun getDrinkNamesByAllIngredients(ingredientsNames: List<String>) : List<String> {
        val allDrinks = DrinkDatabase.getInstance(applicationContext).drinkDao().getAllWithIngredients()
        return allDrinks.filter {
                drink -> drink.ingredients.map {
                ingredient -> ingredient.name
        }.containsAll(ingredientsNames)
        }.map { drinkWithIngredients -> drinkWithIngredients.drink.name  }
    }
}