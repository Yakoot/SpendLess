package dev.mamkin.spendless.data.repository

import dev.mamkin.spendless.data.user.User

interface UserRepository {
    suspend fun isUsernameExists(username: String): Boolean
    suspend fun createUser(username: String, pin: String): User
}