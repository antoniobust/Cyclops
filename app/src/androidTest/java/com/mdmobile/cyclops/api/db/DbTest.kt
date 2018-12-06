package com.mdmobile.cyclops.api.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.mdmobile.cyclops.db.MobiControlDB
import org.junit.After
import org.junit.Before
import org.junit.Rule


abstract class DbTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mcDb: MobiControlDB
    val db: MobiControlDB
        get() = mcDb

    @Before
    fun setUpDb() {
        mcDb = MobiControlDB.getInMemoryDB(InstrumentationRegistry.getInstrumentation().context)
    }

    @After
    fun closeDb() {
        mcDb.close()
    }
}