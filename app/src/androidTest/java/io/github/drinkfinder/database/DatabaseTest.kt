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
class DatabaseTest {
    private lateinit var drinkDao: DrinkDao
    private lateinit var db: DrinkDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DrinkDatabase::class.java
        ).build()

        drinkDao = db.drinkDao()
        drinkDao.insert(Drink(0, "Cosmopolitan"), Drink(0, "Mojito"))
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun readAll() {
        assertEquals(drinkDao.getAll().size, 2)
    }
}