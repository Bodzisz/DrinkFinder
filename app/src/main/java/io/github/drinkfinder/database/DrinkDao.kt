package io.github.drinkfinder.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query

@Dao
interface DrinkDao {

    @Query("SELECT * FROM drinks")
    fun getAll(): List<Drink>


    @Query("SELECT * FROM drinks WHERE name IN (:names)")
    fun getAllByNames(names: List<String>): List<Drink>

    @Query(
        "SELECT * FROM drinks " +
                "JOIN drinks_ingredients ON drinks_ingredients.drinkId = drinks.id " +
                "JOIN ingredients ON ingredients.id = drinks_ingredients.ingredientId " +
                "WHERE ingredients.name IN (:ingredientsNames)"
    )
    fun getAllByIngredients(ingredientsNames: List<String>): List<Drink>

    @Query(
        "SELECT * FROM drinks " +
                "JOIN drinks_ingredients ON drinks_ingredients.drinkId = drinks.id " +
                "JOIN ingredients ON ingredients.id = drinks_ingredients.ingredientId "
    )
    fun getAllWithIngredients(): Map<Drink, List<Ingredient>>

    @Insert(onConflict = IGNORE)
    fun insert(vararg drinks: Drink)
}