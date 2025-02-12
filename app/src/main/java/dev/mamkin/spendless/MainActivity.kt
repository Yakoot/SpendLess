package dev.mamkin.spendless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import dev.mamkin.spendless.di.appModule
import dev.mamkin.spendless.di.databaseModule
import dev.mamkin.spendless.features.root.DefaultRootComponent
import dev.mamkin.spendless.features.root.RootUi
import dev.mamkin.spendless.ui.theme.SpendLessTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        startKoin {
            androidContext(application)
            modules(appModule, databaseModule)
        }
        val root = DefaultRootComponent(defaultComponentContext())
        setContent {
            SpendLessTheme {
                RootUi(root)
            }
        }
    }
}