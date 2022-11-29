package io.github.drinkfinder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Drink::class, Ingredient::class, DrinksIngredients::class], version = 1)
abstract class DrinkDatabase : RoomDatabase() {
    abstract fun drinkDao(): DrinkDao
    abstract fun ingredientDao(): IngredientDao

    companion object {

        @Volatile
        private var INSTANCE: DrinkDatabase? = null

        fun getInstance(context: Context): DrinkDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): DrinkDatabase {
            return Room.databaseBuilder(context, DrinkDatabase::class.java, "Drink_database.db")
                .allowMainThreadQueries() // TODO Remove later after tests
                .createFromAsset("database.db")
                .build()
        }
    }
}