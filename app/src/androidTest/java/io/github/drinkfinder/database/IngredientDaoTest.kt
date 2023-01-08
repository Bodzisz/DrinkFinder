package io.github.drinkfinder.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IngredientDaoTest {
    private lateinit var ingredientDao: IngredientDao
    private lateinit var db: DrinkDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DrinkDatabase::class.java
        ).build()

        ingredientDao = db.ingredientDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun readAll() {
        // assertEquals(drinkDao.getAll().size, 2)
    }
}