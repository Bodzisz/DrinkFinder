package io.github.drinkfinder

import io.github.drinkfinder.database.IngredientWithQuantity
import java.util.*

object UtilityFunctions {
    fun getImageName(drinkName: String): String {
        if (drinkName.isEmpty())
            return ""
        val imageName =
            if (drinkName[0].isDigit())
                "_" + drinkName.subSequence(1, drinkName.length) else drinkName

        return imageName.replace(Regex("[ #-/]"), "_").lowercase(Locale.ROOT)
    }

    fun listAllIngredients(ingredients: List<IngredientWithQuantity>): String {
        var result = ""

        for (i in ingredients) {
            result = concatNextItem(
                result,
                "${i.drinksIngredient.ingredientMeasure} ${i.ingredient.name}"
            )
        }

        return result
    }

    fun concatNextItem(stringToConcat: String, item: String): String {
        if (item.isEmpty())
            return stringToConcat
        val newLine = if (stringToConcat.isEmpty()) "" else "\n"
        return "$stringToConcat$newLine- $item"
    }

    fun adjustInstruction(instruction: String?): String {
        return instruction?.replace(Regex("(\\. )|(: )"), "\n") ?: ""
    }
}