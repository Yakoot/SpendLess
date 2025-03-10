package dev.mamkin.spendless.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.mamkin.spendless.data.user.Converters
import dev.mamkin.spendless.data.user.User
import dev.mamkin.spendless.data.user.UserDao

@Database(entities = [User::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}