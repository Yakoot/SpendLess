package dev.mamkin.spendless.data.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "pin") val pin: String?
)