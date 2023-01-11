package io.github.drinkfinder.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DrinkDaoTest {
    private lateinit var drinkDao: DrinkDao
    private lateinit var db: DrinkDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DrinkDatabase::class.java
        ).allowMainThreadQueries().build()
        drinkDao = db.drinkDao()

        fillDatabase()
    }

    @After
    fun closeDatabase() {
        db.close()
    }

    @Test
    fun shouldReadAllDrinksNamesFromDatabase() {
        val actual = drinkDao.getAllNames()
        val expected = listOf("drink", "drink2")

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReadAllDrinksNamesContainingIngredientsFromDatabase() {
        val actual = drinkDao.getAllNamesByIngredients(listOf("ingredient"))
        val expected = listOf("drink")

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReadDrinkWithIngredientsAndQuantitiesFromDatabase() {
        val actual = drinkDao.getWithIngredientsAndQuantitiesByName("drink")
        val expected = DrinkWithIngredientsAndQuantities(
            Drink(1, "drink", 0, "instructions"),
            listOf(
                IngredientWithQuantity(
                    DrinksIngredients(1, 1, 1, "ingredientMeasure"),
                    Ingredient(1, "ingredient", "description", 0, 0)
                )
            )
        )

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReadAllDrinksWithIngredientsFromDatabase() {
        val actual = drinkDao.getAllWithIngredients()
        val expected = listOf(
            DrinkWithIngredients(
                Drink(1, "drink", 0, "instructions"),
                listOf(Ingredient(1, "ingredient", "description", 0, 0))
            ),
            DrinkWithIngredients(
                Drink(2, "drink2", 0, "instructions"),
                emptyList()
            )
        )

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReadAllFavouritesDrinksNamesFromDatabase() {
        val actual = drinkDao.getAllFavouritesNames()
        val expected = listOf("drink")

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnTrueIfDrinkIsFavourite() {
        assertTrue(drinkDao.isFavourite(1) == 1)
    }

    @Test
    fun shouldInsertNewFavouriteIntoDatabase() {
        drinkDao.insertFavourites(Favourite(2))

        val actual = drinkDao.getAllFavouritesNames()
        val expected = listOf("drink", "drink2")

        assertEquals(expected, actual)
    }

    @Test
    fun shouldDeleteFromFavourites() {
        drinkDao.deleteFromFavourites(Favourite(1))

        val actual = drinkDao.getAllFavouritesNames()
        val expected = emptyList<String>()

        assertEquals(expected, actual)
    }

    private fun fillDatabase() {
        db.openHelper.writableDatabase.execSQL(
            "INSERT INTO drinks VALUES(?, ?, ?, ?)",
            arrayOf(1, "drink", 0, "instructions")
        )

        db.openHelper.writableDatabase.execSQL(
            "INSERT INTO drinks VALUES(?, ?, ?, ?)",
            arrayOf(2, "drink2", 0, "instructions")
        )

        db.openHelper.writableDatabase.execSQL(
            "INSERT INTO ingredients VALUES(?, ?, ?, ?, ?)",
            arrayOf(1, "ingredient", "description", 0, 0)
        )

        db.openHelper.writableDatabase.execSQL(
            "INSERT INTO drinks_ingredients VALUES(?, ?, ?, ?)",
            arrayOf(1, 1, 1, "ingredientMeasure")
        )

        db.openHelper.writableDatabase.execSQL("INSERT INTO favourites VALUES(?)", arrayOf(1))
    }
}