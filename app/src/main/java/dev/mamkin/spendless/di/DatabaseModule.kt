package dev.mamkin.spendless.di

import android.app.Application
import androidx.room.Room
import dev.mamkin.spendless.data.AppDatabase
import dev.mamkin.spendless.data.user.UserDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun provideDataBase(application: Application): AppDatabase =
    Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "spendless-db"
    ).
    fallbackToDestructiveMigration().build()

fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

val databaseModule = module {
    single { provideDataBase(androidContext() as Application) }
    single { provideUserDao(get()) }
}