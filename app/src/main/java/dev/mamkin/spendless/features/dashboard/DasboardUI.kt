package dev.mamkin.spendless.features.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.mamkin.spendless.ui.components.AppButton
import dev.mamkin.spendless.ui.theme.SpendLessTheme
import dev.mamkin.spendless.ui.theme.gradientBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardUI(component: DashboardComponent) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "rockefeller74",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                actions = {
                    AppButton() { }
                }
            )
        },
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .gradientBackground()
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}

@Preview
@Composable
private fun Preview() {
    SpendLessTheme {
        DashboardUI(PreviewDashboardComponent())
    }
}