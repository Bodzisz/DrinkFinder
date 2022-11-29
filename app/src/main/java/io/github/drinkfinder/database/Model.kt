package io.github.drinkfinder.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val description: String?,
    val isAlcoholic: Int,
    val strABV: Int?
)

@Entity(tableName = "drinks")
data class Drink(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val isAlcoholic: Int,
    val instructions: String?
)

@Entity(
    tableName = "drinks_ingredients",
    primaryKeys = ["drinkId", "ingredientId"],
    foreignKeys = [
        ForeignKey(
            entity = Drink::class,
            parentColumns = ["id"],
            childColumns = ["drinkId"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = ["id"],
            childColumns = ["ingredientId"],
            onDelete = ForeignKey.NO_ACTION,
        )
    ]
)
data class DrinksIngredients(
    val drinkId: Int,
    val ingredientId: Int
)