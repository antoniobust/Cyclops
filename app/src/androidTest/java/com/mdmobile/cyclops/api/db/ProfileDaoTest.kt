package com.mdmobile.cyclops.api.db

import com.mdmobile.cyclops.commonTest.TestUtils
import org.hamcrest.MatcherAssert
import org.junit.Test

class ProfileDaoTest : DbTest() {

    private val profile = TestUtils.createProfile()
    private val profileList = TestUtils.createProfileList(100)

    @Test
    fun insertAndRead() {
        val inserted = db.profileDao().insert(profile)
        MatcherAssert.assertThat("Profile Not inserted correctly: $inserted / ${profile.id}", inserted.toInt() == profile.id)

        val bulkInsert = db.profileDao().insertAll(profileList)
        val tmpProfileList = profileList.toMutableList()

        bulkInsert.forEach {
            val found = tmpProfileList.find { profile ->
                profile.id == it.toInt()
            }
            tmpProfileList.remove(found)
        }

        MatcherAssert.assertThat("Bulk profile insert not executed correctly: $bulkInsert", tmpProfileList.isEmpty())

    }

    @Test
    fun delete() {
        insertAndRead()
        var deleted = db.profileDao().deleteByReferenceId(profile.referenceId)
        MatcherAssert.assertThat("Profile {$profile} not deleted: $deleted", deleted == 1)

        deleted = db.profileDao().deleteAll(profileList)
        MatcherAssert.assertThat("Profiles {$profileList} not deleted: $deleted", deleted == profileList.size)
    }

}