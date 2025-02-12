package dev.mamkin.spendless.di

import dev.mamkin.spendless.data.repository.RealUserRepository
import dev.mamkin.spendless.data.repository.UserRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::RealUserRepository) { bind<UserRepository>() }
}