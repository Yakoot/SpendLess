package dev.mamkin.spendless.data

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.mamkin.spendless.data.user.User
import dev.mamkin.spendless.data.user.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}