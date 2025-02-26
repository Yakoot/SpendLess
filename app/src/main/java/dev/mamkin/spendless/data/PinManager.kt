package dev.mamkin.spendless.data

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

interface PinCodeEncryption {
    fun encryptPin(pinCode: String): String
    fun decryptPin(encryptedData: String): String
    fun verifyPin(storedEncryptedPin: String, enteredPin: String): Boolean
}

class PinCodeEncryptionImpl() : PinCodeEncryption {
    private val KEY_ALIAS = "pin_encryption_key"
    private val ANDROID_KEYSTORE = "AndroidKeyStore"
    private val TRANSFORMATION = "AES/GCM/NoPadding"
    private val IV_SEPARATOR = "]"

    // Создание или получение ключа из Android Keystore
    private fun getKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)

        // Проверяем, существует ли уже ключ
        if (!keyStore.containsAlias(KEY_ALIAS)) {
            // Создаем новый ключ, если не существует
            val keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                ANDROID_KEYSTORE
            )

            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setUserAuthenticationRequired(false)
                    .build()
            )

            return keyGenerator.generateKey()
        } else {
            // Получаем существующий ключ
            return (keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry).secretKey
        }
    }

    // Шифрование PIN-кода
    override fun encryptPin(pinCode: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getKey())

        val iv = cipher.iv
        val encryptedBytes = cipher.doFinal(pinCode.toByteArray(Charsets.UTF_8))

        // Сохраняем IV вместе с зашифрованными данными для дешифровки
        val ivAndEncryptedData = Base64.encodeToString(iv, Base64.DEFAULT) +
                IV_SEPARATOR +
                Base64.encodeToString(encryptedBytes, Base64.DEFAULT)

        return ivAndEncryptedData
    }

    // Дешифровка PIN-кода
    override fun decryptPin(encryptedData: String): String {
        val split = encryptedData.split(IV_SEPARATOR)
        if (split.size != 2) throw IllegalArgumentException("Invalid encrypted data format")

        val iv = Base64.decode(split[0], Base64.DEFAULT)
        val encryptedBytes = Base64.decode(split[1], Base64.DEFAULT)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), GCMParameterSpec(128, iv))

        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes, Charsets.UTF_8)
    }

    // Верификация PIN-кода
    override fun verifyPin(storedEncryptedPin: String, enteredPin: String): Boolean {
        val decryptedStoredPin = decryptPin(storedEncryptedPin)
        return decryptedStoredPin == enteredPin
    }
}