package io.github.drinkfinder

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

abstract class DrinkListActivity : AppCompatActivity() {

    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinks_list)

        initToolbar()
        initDrinksList()

        listView = findViewById(R.id.drinks_list_view)
        listView.adapter = ArrayAdapter(this, R.layout.listview_item, getDrinksNames())

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


    abstract fun initDrinksList()
    abstract fun getDrinksNames(): List<String>
}