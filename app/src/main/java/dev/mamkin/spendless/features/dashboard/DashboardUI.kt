package dev.mamkin.spendless.features.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mamkin.spendless.ui.components.AppIconButton
import dev.mamkin.spendless.ui.components.AppIconButtonType
import dev.mamkin.spendless.ui.theme.AppColors
import dev.mamkin.spendless.ui.theme.SpendLessTheme
import dev.mamkin.spendless.ui.theme.bodyXSmall
import dev.mamkin.spendless.ui.theme.gradientBackground
import java.math.BigDecimal
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardUI(component: DashboardComponent) {
    val state by component.state.collectAsStateWithLifecycle()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded
        )
    )
    
    // Рассчитываем половину высоты экрана
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val sheetPeekHeight = (screenHeight / 2).dp
    
    BottomSheetScaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        scaffoldState = scaffoldState,
        sheetPeekHeight = sheetPeekHeight,
        sheetDragHandle = null,
        sheetContent = {
            // Содержимое BottomSheet
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Latest Transactions",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    Text(
                        "Show all",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                
                // Отображаем группы транзакций
                state.transactions.forEach { group ->
                    Text(
                        group.title.uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    
                    // Здесь будут транзакции группы
                    group.transactions.forEach { transaction ->
                        TransactionItem(
                            transaction = transaction,
                            onClick = { component.onEvent(DashboardComponent.UiEvent.TransactionClicked(transaction.id)) }
                        )
                    }
                }
            }
        },
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        sheetContentColor = MaterialTheme.colorScheme.onSurface,
        sheetShadowElevation = 8.dp,
        sheetShape = BottomSheetDefaults.ExpandedShape
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .gradientBackground()
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TopBar
            TopAppBar(
                title = {
                    Text(
                        state.username,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                actions = {
                    AppIconButton(
                        modifier = Modifier.padding(end = 16.dp),
                        icon = Icons.Default.Settings,
                        type = AppIconButtonType.OnPrimary,
                        onClick = { component.onEvent(DashboardComponent.UiEvent.NavigateToSettings) }
                    )
                }
            )
            
            // Карточка с балансом счета - фиксированная высота 150dp
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        formatCurrency(state.accountBalance),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.displayLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        "Account Balance",
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            // Карточки с информацией
            Spacer(modifier = Modifier.height(16.dp))
            
            // Карточка популярной категории
            state.popularCategory?.let { category ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (category.emoji.isNotEmpty()) {
                            Text(
                                category.emoji,
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                        }
                        Column {
                            Text(
                                category.name,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                category.description,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Две карточки в ряд
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Карточка с крупнейшей транзакцией
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(72.dp)
                        .background(
                            color = AppColors.PrimaryFixed,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ) {
                    val largestTransaction = state.largestTransaction
                    if (largestTransaction != null) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(24.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    largestTransaction.title,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.titleLarge,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    "Largest transaction",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.bodyXSmall,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    formatCurrency(largestTransaction.amount),
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    formatDate(largestTransaction.date),
                                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f),
                                    style = MaterialTheme.typography.bodyXSmall
                                )
                            }
                        }
                    } else {
                        Text(
                            "Your largest transaction will appear here",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                
                // Карточка с тратами за неделю
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(72.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            formatCurrency(state.previousWeekSpending),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            "Previous week",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyXSmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionItem(
    transaction: DashboardComponent.TransactionInfo,
    onClick: () -> Unit
) {
    // Здесь будет реализация элемента транзакции
    // Пока оставим заглушку
    Text(
        "${transaction.emoji} ${transaction.title}: ${formatCurrency(transaction.amount)}",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

private fun formatCurrency(amount: BigDecimal): String {
    val format = NumberFormat.getCurrencyInstance()
    return format.format(amount)
}

private fun formatDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
    return date.format(formatter)
}

@Preview
@Composable
private fun Preview() {
    SpendLessTheme {
        DashboardUI(PreviewDashboardComponent())
    }
}