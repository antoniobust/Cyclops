package com.mdmobile.cyclops.api.db

import androidx.test.internal.runner.junit4.AndroidJUnit4Builder
import com.mdmobile.cyclops.db.MobiControlDB
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

class DbStructureTest {

    val mcDb:MobiControlDB

    @Before
    fun setUpDb(){
        mcDb = MobiControlDB.create()
    }
    @Test
    fun test(){

    }
}