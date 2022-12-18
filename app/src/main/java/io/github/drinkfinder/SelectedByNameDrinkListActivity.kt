package io.github.drinkfinder

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import io.github.drinkfinder.database.DrinkDatabase

class SelectedByNameDrinkListActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_by_name)

        initToolbar()

        val drinkNames: List<String> =
            DrinkDatabase.getInstance(applicationContext).drinkDao().getAllNames()

        adapter = ArrayAdapter(this, R.layout.listview_item, drinkNames)
        listView = findViewById(R.id.listview_drinks_by_names)
        listView.adapter = adapter

        initSearchView(drinkNames)

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val intent = Intent(this, DrinkViewActivity::class.java)
                intent.putExtra(
                    "selectedDrink",
                    listView.getItemAtPosition(position).toString()
                )
                startActivity(intent)
            }
    }

    private fun initToolbar() {
        setSupportActionBar(findViewById(R.id.myToolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true;
    }

    private fun initSearchView(drinkNames: List<String>) {
        searchView = findViewById(R.id.drink_search)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (drinkNames.contains(query)) {
                    adapter.filter.filter(query)
                } else {
                    Toast.makeText(
                        this@SelectedByNameDrinkListActivity,
                        "No Match found",
                        Toast.LENGTH_LONG
                    ).show()
                }
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }
}