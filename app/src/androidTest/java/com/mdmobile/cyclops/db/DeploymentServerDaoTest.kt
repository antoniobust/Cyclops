package com.mdmobile.cyclops.db

import com.mdmobile.cyclops.commonTest.TestUtils
import org.hamcrest.MatcherAssert
import org.junit.Test
import com.mdmobile.cyclops.commonTest.LiveDataTestUtil.getValue

class DeploymentServerDaoTest : DbTest() {

    private val ds = TestUtils.createDeploymentServer()
    private val instance = TestUtils.createInstance()
    @Test
    fun insertAndRead() {
        db.instanceDao().insert(instance)
        val insertedId = db.deploymentServerDao().insert(ds).toInt()
        MatcherAssert.assertThat("DS not inserted correctly: $insertedId", insertedId == ds.id)

        val dSs = getValue(db.deploymentServerDao().getAllDs())
        MatcherAssert.assertThat("DS not found in DB: $dSs", !dSs.isNullOrEmpty() && dSs.find {
            it.id == ds.id
        }?.name == ds.name)
    }

    @Test
    fun delete() {
        insertAndRead()
        val deleted = db.deploymentServerDao().delete(ds)
        MatcherAssert.assertThat("DS not deleted: $deleted", deleted == 1)
    }

    @Test()
    fun deleteDsAfterInstanceRemoval() {
        insertAndRead()

        db.instanceDao().delete(instance)
        val dSs = getValue(db.deploymentServerDao().getAllDs())
        MatcherAssert.assertThat("DS found in DB, it should be empty: $dSs", dSs.isNullOrEmpty())
    }
}