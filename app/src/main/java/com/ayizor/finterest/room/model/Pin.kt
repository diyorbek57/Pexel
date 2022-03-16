package com.ayizor.finterest.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pin(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "pin_id") val pin_id: String?,
    @ColumnInfo(name = "pin_url") val pin_url: String?,
    @ColumnInfo(name = "pin_board") val pin_board: String?
)