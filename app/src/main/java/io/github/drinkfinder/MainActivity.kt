package io.github.drinkfinder

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import io.github.drinkfinder.database.DrinkDatabase
import io.github.drinkfinder.ingredientList.IngredientListAdapter
import io.github.drinkfinder.ingredientList.ListIngredientDataModel

class MainActivity : AppCompatActivity() {

    private val ingredientsDataModel : ArrayList<ListIngredientDataModel> = ArrayList()

    lateinit var searchView: SearchView
    lateinit var listView: ListView
    lateinit var adapter: IngredientListAdapter
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ingredientsNames : List<String> =
            DrinkDatabase.getInstance(applicationContext).ingredientDao().getAllIngredientNames()

        fillIngredientsDataModel(ingredientsNames)
        adapter = IngredientListAdapter(ingredientsDataModel, applicationContext)
        initListView()
        initSearchView(ingredientsNames)
        initNavigationMenu()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun switchScreens(activityClass: Class<*>?) {
        val nextScreen = Intent(this, activityClass)
        startActivity(nextScreen)
    }
    
    private fun initListView() {
        listView = findViewById(R.id.listview_1)
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val dataModelIngredient: ListIngredientDataModel = ingredientsDataModel[position]
            dataModelIngredient.checked = !dataModelIngredient.checked
            adapter.notifyDataSetChanged()
        }
    }
    
    private fun initSearchView(ingredientsNames : List<String>) {
        searchView = findViewById(R.id.ingredientSearch)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (ingredientsNames.contains(query)) {
                    adapter.filter.filter(query)
                } else {
                    Toast.makeText(this@MainActivity, "No Match found", Toast.LENGTH_LONG).show()
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

    private fun initNavigationMenu() {
        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                /* TODO
                    Test Activity used
                    Change it to real activities when created
                */
                R.id.item1 -> switchScreens(TestActivity::class.java)
                R.id.item2 -> switchScreens(TestActivity::class.java)
                R.id.item3 -> switchScreens(TestActivity::class.java)
            }
            true
        }
    }

    private fun fillIngredientsDataModel(ingredientsNames : List<String>) {
        if(ingredientsDataModel.isEmpty()) {
            for (ingredient in ingredientsNames) {
                ingredientsDataModel.add(ListIngredientDataModel(ingredient, false))
            }
        }
    }
}