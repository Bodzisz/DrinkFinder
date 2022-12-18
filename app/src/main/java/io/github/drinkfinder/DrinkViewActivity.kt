package io.github.drinkfinder

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import io.github.drinkfinder.database.DrinkDatabase
import io.github.drinkfinder.database.Favourite
import io.github.drinkfinder.database.Ingredient
import java.util.*

class DrinkViewActivity : DrinkFinderSecondaryActivity() {

    private var isFavourite = false

    companion object {
        const val ALCOHOLIC_TEXT = "alcoholic"
        const val NON_ALCOHOLIC_TEXT = "non-$ALCOHOLIC_TEXT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_page)

        initToolbar()

        val drinkDao = DrinkDatabase.getInstance(applicationContext).drinkDao()
        val selectedDrink =
            drinkDao.getWithIngredientsByName(intent.getStringExtra("selectedDrink"))

        val isAlcoholicText = findViewById<TextView>(R.id.is_alcoholic_text)
        val isAlcoholicImage = findViewById<ImageView>(R.id.is_alcoholic_image)

        if (selectedDrink.drink.isAlcoholic == 0) {
            isAlcoholicText.text = NON_ALCOHOLIC_TEXT
            isAlcoholicText.setTextColor(resources.getColor(R.color.green, theme))
            isAlcoholicImage.setImageResource(R.drawable.drink_icon_non_alcoholic)
        } else {
            isAlcoholicText.text = ALCOHOLIC_TEXT
            isAlcoholicText.setTextColor(resources.getColor(R.color.red, theme))
            isAlcoholicImage.setImageResource(R.drawable.drink_icon_alcoholic)
        }

        val drinkImageId = applicationContext.resources.getIdentifier(
            getImageName(selectedDrink.drink.name),
            "drawable",
            packageName
        )

        findViewById<ImageView>(R.id.drink_image).setImageResource(if (drinkImageId != 0) drinkImageId else R.drawable.default_drink)
        findViewById<TextView>(R.id.drink_ingredients).text =
            listAllIngredients(selectedDrink.ingredients)
        findViewById<TextView>(R.id.drink_instructions).text =
            adjustInstruction(selectedDrink.drink.instructions)

        val drinkName = findViewById<TextView>(R.id.drink_name)
        drinkName.text = selectedDrink.drink.name

        if (selectedDrink.drink.name.length > 25) {
            drinkName.textSize = 22.0f
        }

        val favouriteButton = findViewById<ImageButton>(R.id.favourite_button)

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

    private fun getImageName(drinkName: String): String {
        val imageName =
            if (drinkName[0].isDigit())
                "_" + drinkName.subSequence(1, drinkName.length) else drinkName

        return imageName.replace(Regex("[ #-/]"), "_").lowercase(Locale.ROOT)
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