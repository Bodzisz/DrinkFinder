package io.github.drinkfinder.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Transaction

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

    @Transaction
    @Query("SELECT * FROM drinks")
    fun getAllWithIngredients(): List<DrinksWithIngredients>

    @Transaction
    @Query("SELECT * FROM drinks WHERE drinks.name IN (:drinkNames)")
    fun getWithIngredientsByNames(drinkNames: List<String>): List<DrinksWithIngredients>

    @Insert(onConflict = IGNORE)
    fun insert(vararg drinks: Drink)
}