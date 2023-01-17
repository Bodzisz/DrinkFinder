package io.github.drinkfinder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView
import io.github.drinkfinder.database.DrinkDatabase
import io.github.drinkfinder.database.Favourite
import io.github.drinkfinder.database.IngredientWithQuantity
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
            drinkDao.getWithIngredientsAndQuantitiesByName(intent.getStringExtra("selectedDrink"))

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
            UtilityFunctions.getImageName(selectedDrink.drink.name),
            "drawable",
            packageName
        )

        findViewById<ShapeableImageView>(R.id.drink_image).setImageResource(
            if (drinkImageId != 0) drinkImageId else R.drawable.default_drink
        )

        findViewById<TextView>(R.id.drink_ingredients).text =
            UtilityFunctions.listAllIngredients(selectedDrink.ingredientsWithQuantity)

        print(findViewById<TextView>(R.id.drink_ingredients).text)
        findViewById<TextView>(R.id.drink_instructions).text =
            UtilityFunctions.adjustInstruction(selectedDrink.drink.instructions)

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

}