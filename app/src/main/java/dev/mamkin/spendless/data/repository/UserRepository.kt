package dev.mamkin.spendless.data.repository

import dev.mamkin.spendless.data.user.User

interface UserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun isUsernameExists(username: String): Boolean
}