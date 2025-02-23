package dev.mamkin.spendless.data.repository

import dev.mamkin.spendless.data.user.User
import dev.mamkin.spendless.data.user.UserDao
import dev.mamkin.spendless.features.registration.preferences.Preferences

class RealUserRepository(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun isUsernameExists(username: String): Boolean {
        return userDao.countUsersByUsername(username) > 0
    }

    override suspend fun createUser(username: String, pin: String, preferences: Preferences): User {
        val newUser = User(username = username, pin = pin, expensesFormat = preferences.expensesFormat, currency = preferences.currency, decimalSeparator = preferences.decimalSeparator, thousandsSeparator = preferences.thousandsSeparator)
        val generatedId = userDao.insertUser(newUser)
        return newUser.copy(id = generatedId.toInt())
    }
}