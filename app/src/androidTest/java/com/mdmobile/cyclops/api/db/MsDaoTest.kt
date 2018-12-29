package com.mdmobile.cyclops.api.db

import com.mdmobile.cyclops.commonTest.LiveDataTestUtil.getValue
import com.mdmobile.cyclops.commonTest.TestUtils
import org.hamcrest.MatcherAssert
import org.junit.Test

class MsDaoTest :DbTest(){

    private val ms = TestUtils.createManagementServer()
    private val instance = TestUtils.createInstance()
    @Test
    fun insertAndRead() {
        db.instanceDao().insert(instance)
        val insertedId = db.managementServerDao().insert(ms).toInt()
        MatcherAssert.assertThat("MS not inserted correctly: $insertedId", insertedId == ms.id)

        val dSs = getValue(db.managementServerDao().getAllMs())
        MatcherAssert.assertThat("MS not found in DB: $dSs", !dSs.isNullOrEmpty() && dSs.find {
            it.id == ms.id
        }?.name == ms.name)
    }

    @Test
    fun delete() {
        insertAndRead()
        val deleted = db.managementServerDao().delete(ms)
        MatcherAssert.assertThat("MS not deleted: $deleted", deleted == 1)
    }

    @Test()
    fun deleteDsAfterInstanceRemoval() {
        insertAndRead()
        db.instanceDao().delete(instance)
        val dSs = getValue(db.managementServerDao().getAllMs())
        MatcherAssert.assertThat("MS found in DB, it should be empty: $dSs", dSs.isNullOrEmpty())
    }
}