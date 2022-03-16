package com.ayizor.finterest.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ayizor.finterest.room.model.Pin

@Dao
interface PinDao {
    @Query("SELECT * FROM pin")
    fun getAll(): List<Pin>

    @Query("SELECT * FROM pin WHERE id LIKE :id")
    fun findById(id: IntArray): List<Pin>

    @Query("DELETE FROM pin")
    fun nukeTable()

    @Insert
    fun insertAll(vararg pin: Pin)

    @Delete
    fun delete(pin: Pin)

}