package dev.mamkin.spendless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import dev.mamkin.spendless.features.root.DefaultRootComponent
import dev.mamkin.spendless.features.root.RootUi
import dev.mamkin.spendless.ui.theme.SpendLessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val root = DefaultRootComponent(defaultComponentContext())
        setContent {
            SpendLessTheme {
                RootUi(root)
            }
        }
    }
}