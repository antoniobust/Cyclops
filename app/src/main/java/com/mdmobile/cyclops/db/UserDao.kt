package com.mdmobile.cyclops.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mdmobile.cyclops.dataModel.api.newDataClass.User

@Dao
interface UserDao : BaseDao<User> {
    @Query("SELECT * FROM UserInfo")
    fun getUsers(): LiveData<List<User>>

    @Query("SELECT * FROM UserInfo WHERE instanceId =:instanceId")
    fun  getUserByInstance(instanceId:Int): LiveData<List<User>>

    @Query("SELECT * FROM UserInfo WHERE id = :id")
    fun getUser(id: String): LiveData<User>
}