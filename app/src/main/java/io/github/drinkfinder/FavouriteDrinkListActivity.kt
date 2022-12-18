package io.github.drinkfinder

import android.widget.ArrayAdapter
import io.github.drinkfinder.database.DrinkDatabase

class FavouriteDrinkListActivity : DrinkListActivity() {

    private lateinit var drinksList: List<String>

    override fun initDrinksList() {
        drinksList =
            DrinkDatabase.getInstance(applicationContext).drinkDao().getAllFavouritesNames()
    }

    override fun getDrinksNames(): List<String> {
        return drinksList
    }

    override fun onRestart() {
        super.onRestart()
        initDrinksList()
        listView.adapter = ArrayAdapter(this, R.layout.listview_item, getDrinksNames())
    }
}