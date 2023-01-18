package io.github.drinkfinder

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import io.github.drinkfinder.database.DrinkDatabase
import io.github.drinkfinder.ingredientList.IngredientListAdapter
import io.github.drinkfinder.ingredientList.ListIngredientDataModel

class MainActivity : AppCompatActivity() {

    private val ingredientsDataModel: ArrayList<ListIngredientDataModel> = ArrayList()

    lateinit var searchView: SearchView
    lateinit var listView: ListView
    lateinit var adapter: IngredientListAdapter
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()

        val ingredientsNames: List<String> =
            DrinkDatabase.getInstance(applicationContext).ingredientDao().getAllIngredientNames()

        fillIngredientsDataModel(ingredientsNames)
        adapter = IngredientListAdapter(ingredientsDataModel, applicationContext)

        initListView()
        initSearchView(ingredientsNames)
        initNavigationMenu()
        initSearchButton()
        initClearButton()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_selected_ingredients_option -> {
                startActivityWithSelctedIngredients(ShowSelectedIngredientsActivity::class.java)
            }
        }
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun switchScreens(activityClass: Class<*>?) {
        val nextScreen = Intent(this, activityClass)
        startActivity(nextScreen)
    }

    private fun initToolbar() {
        setSupportActionBar(findViewById(R.id.myToolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
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

    private fun initSearchView(ingredientsNames: List<String>) {
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
        drawerLayout = findViewById(R.id.main_layout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.select_by_ingredients ->
                    if (!this::class.simpleName.equals("MainActivity")) switchScreens(MainActivity::class.java)
                R.id.select_by_name -> switchScreens(SelectedByNameDrinkListActivity::class.java)
                R.id.favourite_drinks -> switchScreens(FavouriteDrinkListActivity::class.java)
                R.id.about -> switchScreens(AboutActivity::class.java)
            }
            true
        }

        initAllIngredientsSwitch(navView)
    }

    private fun initAllIngredientsSwitch(navView: NavigationView) {
        val menuItem = navView.menu.findItem(R.id.allIngredientsSwitch)
        val switchId = menuItem.actionView.findViewById(R.id.switch_id) as SwitchCompat
        val preferences = getSharedPreferences("Preferences", MODE_PRIVATE)
        switchId.isChecked = preferences.getBoolean("allIngredients", false)
        switchId.setOnClickListener(View.OnClickListener {
            val editor = preferences.edit()
            editor.putBoolean("allIngredients", switchId.isChecked)
            editor.apply()
        })
    }

    private fun initSearchButton() {
        val searchButton = findViewById<Button>(R.id.search_button)
        searchButton.setOnClickListener {
            startActivityWithSelctedIngredients(SelectedIngredientsDrinkListActivity::class.java)
        }
    }

    private fun initClearButton() {
        val searchButton = findViewById<Button>(R.id.clear_button)
        searchButton.setOnClickListener {
            ingredientsDataModel.forEach(this::clearIngredientState)
            adapter.notifyDataSetChanged()
        }
    }

    private fun fillIngredientsDataModel(ingredientsNames: List<String>) {
        if (ingredientsDataModel.isEmpty()) {
            for (ingredient in ingredientsNames) {
                ingredientsDataModel.add(ListIngredientDataModel(ingredient, false))
            }
        }
    }

    private fun getSelectedIngredientsNames(): ArrayList<CharSequence?> {
        val selected = ArrayList<CharSequence?>()
        for (ingredient in ingredientsDataModel) {
            if (ingredient.checked) {
                selected.add(ingredient.name)
            }
        }
        return selected
    }

    private fun clearIngredientState(listIngredientDataModel: ListIngredientDataModel) {
        listIngredientDataModel.checked = false
    }

    private fun startActivityWithSelctedIngredients(activityClass: Class<*>?) {
        val intent = Intent(this, activityClass)
        intent.putCharSequenceArrayListExtra(
            "selectedIngredientsNames",
            getSelectedIngredientsNames()
        )
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dots_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}