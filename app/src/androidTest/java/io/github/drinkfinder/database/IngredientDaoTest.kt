package io.github.drinkfinder.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IngredientDaoTest {
    private lateinit var ingredientDao: IngredientDao
    private lateinit var db: DrinkDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DrinkDatabase::class.java
        ).allowMainThreadQueries().build()
        ingredientDao = db.ingredientDao()
    }

    @After
    fun closeDatabase() {
        db.close()
    }

    @Test
    fun shouldReadAllIngredientNamesFromDatabase() {
        db.openHelper.writableDatabase.execSQL(
            "INSERT INTO ingredients VALUES(?, ?, ?, ?, ?)",
            arrayOf(1, "ingredient", "description", 0, 0)
        )

        val actual = ingredientDao.getAllIngredientNames()
        val expected = listOf("ingredient")

        assertEquals(expected, actual)
    }
}