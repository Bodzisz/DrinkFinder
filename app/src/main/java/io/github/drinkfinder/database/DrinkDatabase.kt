package io.github.drinkfinder.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Drink::class], version = 1)
abstract class DrinkDatabase : RoomDatabase() {
    abstract fun drinkDao(): DrinkDao
}