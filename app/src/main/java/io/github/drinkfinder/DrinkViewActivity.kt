package io.github.drinkfinder

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.drinkfinder.database.DrinkDatabase
import io.github.drinkfinder.database.Ingredient

class DrinkViewActivity : AppCompatActivity() {

    private lateinit var drinkImage: ImageView
    private lateinit var drinkName: TextView
    private lateinit var drinkIngredients: TextView
    private lateinit var drinkInstructions: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_page)

        val selectedDrink =
            DrinkDatabase.getInstance(applicationContext).drinkDao().getWithIngredientsByName(
                intent.getStringExtra("selectedDrink")
            )

        drinkImage = findViewById(R.id.drink_image)
        // if drink image not found set the default one
        drinkImage.setImageResource(R.drawable.default_drink)

        drinkName = findViewById(R.id.drink_name)
        drinkName.text = selectedDrink.drink.name

        drinkIngredients = findViewById(R.id.drink_ingredients)
        drinkIngredients.text = listAllIngredients(selectedDrink.ingredients)

        drinkInstructions = findViewById(R.id.drink_instructions)
        drinkInstructions.text = adjustInstruction(selectedDrink.drink.instructions)
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