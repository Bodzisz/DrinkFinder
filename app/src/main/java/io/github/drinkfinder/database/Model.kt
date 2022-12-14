package io.github.drinkfinder.database

import androidx.room.*

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
    @PrimaryKey val drinksIngredientsId: Int,
    val drinkId: Int,
    val ingredientId: Int,
    val ingredientMeasure: String
)

data class IngredientWithQuantity(
    @Embedded val drinksIngredient: DrinksIngredients,
    @Relation(parentColumn = "ingredientId", entity = Ingredient::class, entityColumn = "id")
    val ingredient: Ingredient
)

data class DrinkWithIngredientsAndQuantities(
    @Embedded val drink: Drink,
    @Relation(parentColumn = "id", entity = DrinksIngredients::class, entityColumn = "drinkId")
    val ingredientsWithQuantity: List<IngredientWithQuantity>
)

data class DrinkWithIngredients(
    @Embedded val drink: Drink,
    @Relation(
        parentColumn = "id",
        entity = Ingredient::class,
        entityColumn = "id",
        associateBy = Junction(
            value = DrinksIngredients::class,
            parentColumn = "drinkId",
            entityColumn = "ingredientId"
        )
    )
    val ingredients: List<Ingredient>
)

@Entity(
    tableName = "favourites",
    foreignKeys = [
        ForeignKey(
            entity = Drink::class,
            parentColumns = ["id"],
            childColumns = ["drinkId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class Favourite(
    @PrimaryKey(autoGenerate = false) val drinkId: Int,
)

