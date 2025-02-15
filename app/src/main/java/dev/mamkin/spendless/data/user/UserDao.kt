package dev.mamkin.spendless.data.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE username LIKE :first LIMIT 1")
    fun findByName(first: String): User

    @Delete
    fun delete(user: User)

    @Query("SELECT COUNT(*) FROM user WHERE username = :username")
    suspend fun countUsersByUsername(username: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long
}