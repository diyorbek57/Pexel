package com.ayizor.finterest.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ayizor.finterest.room.model.Pin

@Database(entities = [Pin::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pinDao(): PinDao
}