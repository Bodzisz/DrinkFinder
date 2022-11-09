package io.github.drinkfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    /* TODO
        Mock values used.
        Change it to real data from db/api
    */
    var ingredients = arrayOf(
        "Peach",
        "Apple",
        "Grapes",
        "Orange",
        "Mint",
        "Banana",
        "Vodka",
        "Gin",
        "Whiskey",
        "Rum",
        "Champagne",
        "Coca-cola",
        "Sprite",
        "Tonic",
        "Milk",
        "Tomato",
        "Lemon"
    )

    lateinit var searchView: SearchView
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter(
            this,
            R.layout.listview_item, ingredients
        )

        listView= findViewById(R.id.listview_1)
        listView.adapter = adapter

        searchView = findViewById(R.id.ingredientSearch)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (ingredients.contains(query)) {
                    adapter.filter.filter(query)
                } else {
                    Toast.makeText(this@MainActivity, "No Match found", Toast.LENGTH_LONG).show()
                }
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }
}