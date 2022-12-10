package io.github.drinkfinder

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import io.github.drinkfinder.database.Drink

abstract class DrinkListActivity : AppCompatActivity() {

    lateinit var drinksList : List<Drink>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinks_list)

        initDrinksList()

        listView = findViewById(R.id.drinks_list_view)
        listView.adapter = ArrayAdapter(this, R.layout.listview_item, getDrinksNames())
    }

    abstract fun initDrinksList()
    abstract fun getDrinksNames() : List<String>
}