package com.mdmobile.cyclops.db

import androidx.room.*

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(objList: List<T>): List<Long>

    @Delete
    fun delete(obj: T): Int

    @Delete
    fun deleteAll(objList: List<T>): Int

    @Update
    fun update(obj: T): Int

    @Update
    fun updateAll(objList: List<T>): Int
}