package io.github.drinkfinder

import androidx.appcompat.app.AppCompatActivity

abstract class DrinkFinderSecondaryActivity : AppCompatActivity() {

    protected fun initToolbar() {
        setSupportActionBar(findViewById(R.id.myToolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true;
    }
}