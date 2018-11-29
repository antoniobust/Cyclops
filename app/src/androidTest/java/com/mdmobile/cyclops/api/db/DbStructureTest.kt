package com.mdmobile.cyclops.api.db

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.mdmobile.cyclops.db.MobiControlDB
import org.junit.After
import org.junit.Before

abstract class DbStructureTest {

    private lateinit var mcDb: MobiControlDB
    val db: MobiControlDB
        get() = mcDb

    @Before
    fun setUpDb() {
        mcDb = MobiControlDB.database
    }

//    @After
//    fun closeDb() {
//        mcDb.close()
//    }
}