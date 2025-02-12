package dev.mamkin.spendless.data.repository

import dev.mamkin.spendless.data.user.UserDao

class RealUserRepository(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun getAllUsers() = userDao.getAll()

    override suspend fun isUsernameExists(username: String): Boolean {
        return userDao.countUsersByUsername(username) > 0
    }
}