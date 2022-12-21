package io.github.drinkfinder

import android.os.Bundle

class AboutActivity : DrinkFinderSecondaryActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)

        initToolbar()

    }
}