package io.github.drinkfinder.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query

@Dao
interface DrinkDao {

    @Query("SELECT * FROM drinks")
    fun getAll(): List<Drink>

    @Query("SELECT * FROM drinks WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Drink>

    @Insert(onConflict = IGNORE)
    fun insert(vararg drinks: Drink)
}