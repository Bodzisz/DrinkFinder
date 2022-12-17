package io.github.drinkfinder

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.drinkfinder.database.DrinkDatabase
import io.github.drinkfinder.database.Favourite
import io.github.drinkfinder.database.Ingredient

class DrinkViewActivity : AppCompatActivity() {

    private lateinit var drinkImage: ImageView
    private lateinit var drinkName: TextView
    private lateinit var drinkIngredients: TextView
    private lateinit var drinkInstructions: TextView
    private lateinit var favouriteButton: ImageButton
    private var isFavourite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_page)

        val drinkDao = DrinkDatabase.getInstance(applicationContext).drinkDao()
        val selectedDrink =
            drinkDao.getWithIngredientsByName(intent.getStringExtra("selectedDrink"))

        drinkImage = findViewById(R.id.drink_image)
        // if drink image not found set the default one
        drinkImage.setImageResource(R.drawable.default_drink)

        drinkName = findViewById(R.id.drink_name)
        drinkName.text = selectedDrink.drink.name

        drinkIngredients = findViewById(R.id.drink_ingredients)
        drinkIngredients.text = listAllIngredients(selectedDrink.ingredients)

        drinkInstructions = findViewById(R.id.drink_instructions)
        drinkInstructions.text = adjustInstruction(selectedDrink.drink.instructions)

        favouriteButton = findViewById(R.id.favourite_button)

        if (drinkDao.isFavourite(selectedDrink.drink.id) == 1) {
            favouriteButton.setImageResource(android.R.drawable.star_big_on)
            isFavourite = true
        }

        favouriteButton.setOnClickListener {
            isFavourite = if (isFavourite) {
                drinkDao.deleteFromFavourites(Favourite(selectedDrink.drink.id))
                favouriteButton.setImageResource(android.R.drawable.star_big_off)
                false
            } else {
                drinkDao.insertFavourites(Favourite(selectedDrink.drink.id))
                favouriteButton.setImageResource(android.R.drawable.star_big_on)
                true
            }
        }
    }

    private fun listAllIngredients(ingredients: List<Ingredient>): String {
        var result = ""

        for (i in ingredients) {
            result = concatNextItem(result, i.name)
        }

        return result
    }

    private fun concatNextItem(stringToConcat: String, item: String): String {
        val newLine = if (stringToConcat.isEmpty()) "" else "\n"
        return "$stringToConcat$newLine- $item"
    }

    private fun adjustInstruction(instruction: String?): String {
        return instruction?.replace(Regex("(\\. )|(: )"), "\n") ?: ""
    }
}