package com.mdmobile.cyclops.api.db

import com.mdmobile.cyclops.commonTest.TestUtils
import org.hamcrest.MatcherAssert
import org.junit.Test

class ScriptDaoTest : DbTest() {

    private val script = TestUtils.createScript()

    @Test
    fun insertAndRead() {
        val inserted = db.scriptDao().insert(script).toInt()
        MatcherAssert.assertThat("Script not inserted correctly: $inserted", inserted == script.id)

        val insertedScript = TestUtils.getValue(db.scriptDao().getScripts())
        MatcherAssert.assertThat("Script not found in DB: $insertedScript", !insertedScript.isNullOrEmpty() && insertedScript.find {
            it.id == script.id
        }?.script == script.script)
    }

    @Test
    fun delete() {
        insertAndRead()
        val deleted = db.scriptDao().delete(script)
        MatcherAssert.assertThat("Script  not deleted: $deleted", deleted == 1)

        val savedScripts = TestUtils.getValue(db.scriptDao().getScripts())
        MatcherAssert.assertThat("Script found in DB, it should be empty: $savedScripts", savedScripts.isNullOrEmpty())
    }
}
