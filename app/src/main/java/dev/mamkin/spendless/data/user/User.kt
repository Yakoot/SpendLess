package dev.mamkin.spendless.data.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import dev.mamkin.spendless.features.registration.preferences.Currency
import dev.mamkin.spendless.features.registration.preferences.DecimalSeparator
import dev.mamkin.spendless.features.registration.preferences.ExpensesFormat
import dev.mamkin.spendless.features.registration.preferences.ThousandsSeparator

@Entity
data class User(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "pin") val pin: String,
    @ColumnInfo(name = "expensesFormat") val expensesFormat: ExpensesFormat,
    @ColumnInfo(name = "currency") val currency: Currency,
    @ColumnInfo(name = "decimalSeparator") val decimalSeparator: DecimalSeparator,
    @ColumnInfo(name = "thousandsSeparator") val thousandsSeparator: ThousandsSeparator
)

@Suppress("unused")
class Converters {
    @TypeConverter
    fun currencyFromString(value: String): Currency {
        return Currency.valueOf(value)
    }

    @TypeConverter
    fun currencyToString(currency: Currency): String {
        return currency.name
    }

    @TypeConverter
    fun decimalSeparatorFromString(value: String): DecimalSeparator {
        return DecimalSeparator.valueOf(value)
    }

    @TypeConverter
    fun decimalSeparatorToString(decimalSeparator: DecimalSeparator): String {
        return decimalSeparator.name
    }

    @TypeConverter
    fun expensesFormatFromString(value: String): ExpensesFormat {
        return ExpensesFormat.valueOf(value)
    }

    @TypeConverter
    fun expensesFormatToString(expensesFormat: ExpensesFormat): String {
        return expensesFormat.name
    }

    @TypeConverter
    fun thousandsSeparatorFromString(value: String): ThousandsSeparator {
        return ThousandsSeparator.valueOf(value)
    }

    @TypeConverter
    fun thousandsSeparatorToString(thousandsSeparator: ThousandsSeparator): String {
        return thousandsSeparator.name
    }
}