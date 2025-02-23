package dev.mamkin.spendless.data.repository

import dev.mamkin.spendless.data.user.User
import dev.mamkin.spendless.features.registration.preferences.Preferences

interface UserRepository {
    suspend fun isUsernameExists(username: String): Boolean
    suspend fun createUser(username: String, pin: String, preferences: Preferences): User
}