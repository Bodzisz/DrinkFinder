package io.github.drinkfinder

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class ShowSelectedIngredientsActivity : DrinkFinderSecondaryActivity() {

    lateinit var listView: ListView

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_ingredients_list)

        val ingredientsNames =
            intent.getCharSequenceArrayListExtra("selectedIngredientsNames") as ArrayList<String>

        initToolbar()
        initListView(ingredientsNames)

        if (ingredientsNames.isEmpty()) {
            Toast.makeText(applicationContext,
            "No ingredients selected",
            Toast.LENGTH_LONG).show()
        }
    }

    private fun initListView(ingredientsNames: List<String>) {
        listView = findViewById(R.id.selected_ingredients_list)
        listView.adapter = ArrayAdapter(this, R.layout.listview_item,
            ingredientsNames)
    }
}