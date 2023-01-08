package io.github.drinkfinder.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface IngredientDao {

    @Query("SELECT name FROM ingredients")
    fun getAllIngredientNames(): List<String>
}