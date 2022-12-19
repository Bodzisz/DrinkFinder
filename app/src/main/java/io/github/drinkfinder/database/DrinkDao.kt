package io.github.drinkfinder.database

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE

@Dao
interface DrinkDao {

    @Query("SELECT name FROM drinks")
    fun getAllNames(): List<String>

    @Query(
        "SELECT drinks.name FROM drinks " +
                "JOIN drinks_ingredients ON drinks_ingredients.drinkId = drinks.id " +
                "JOIN ingredients ON ingredients.id = drinks_ingredients.ingredientId " +
                "WHERE ingredients.name IN (:ingredientsNames)"
    )
    fun getAllNamesByIngredients(ingredientsNames: List<String>): List<String>

    @Query(
        "SELECT drinks.name FROM drinks " +
                "JOIN favourites ON favourites.drinkId = drinks.id"
    )
    fun getAllFavouritesNames(): List<String>

    @Transaction
    @Query("SELECT * FROM drinks WHERE drinks.name = :drinkName")
    fun getWithIngredientsAndQuantitiesByName(drinkName: String?): DrinkWithIngredientsAndQuantities

    @Transaction
    @Query("SELECT * FROM drinks")
    fun getAllWithIngredients(): List<DrinkWithIngredients>

    @Query("SELECT COUNT(drinkId) from favourites WHERE drinkId = :id")
    fun isFavourite(id: Int): Int

    @Insert(onConflict = IGNORE, entity = Favourite::class)
    fun insertFavourites(vararg favourites: Favourite)

    @Delete(entity = Favourite::class)
    fun deleteFromFavourites(vararg favourites: Favourite)
}