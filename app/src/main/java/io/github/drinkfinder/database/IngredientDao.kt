package io.github.drinkfinder.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IngredientDao {

    @Query("SELECT * FROM ingredients")
    fun getAll(): List<Ingredient>

    @Query("SELECT name FROM ingredients")
    fun getAllIngredientNames(): List<String>

    @Query("SELECT * FROM ingredients WHERE name IN (:names)")
    fun getAllByNames(names: List<String>): List<Ingredient>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg ingredients: Ingredient)
}