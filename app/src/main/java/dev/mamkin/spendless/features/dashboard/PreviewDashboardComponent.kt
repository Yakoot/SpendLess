package dev.mamkin.spendless.features.dashboard

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.math.BigDecimal
import java.time.LocalDate

class PreviewDashboardComponent : DashboardComponent {
    private val _state = MutableStateFlow(createPreviewState())
    override val state: StateFlow<DashboardComponent.State> = _state

    override fun onEvent(event: DashboardComponent.UiEvent) {
        // Ничего не делаем в превью
    }

    private fun createPreviewState(): DashboardComponent.State {
        return DashboardComponent.State(
            username = "rockefeller74",
            accountBalance = BigDecimal("10382.45"),
            popularCategory = DashboardComponent.CategoryInfo(
                name = "Food & Groceries",
                description = "Most popular category",
                emoji = "🍕"
            ),
            largestTransaction = DashboardComponent.TransactionInfo(
                id = "1",
                title = "Adobe Yearly",
                amount = BigDecimal("-59.99"),
                date = LocalDate.of(2025, 1, 7),
                categoryName = "Subscription",
                emoji = "💻",
                description = "Adobe Creative Cloud yearly subscription"
            ),
            previousWeekSpending = BigDecimal("-762.20"),
            transactions = listOf(
                DashboardComponent.TransactionGroup(
                    title = "Today",
                    transactions = listOf(
                        DashboardComponent.TransactionInfo(
                            id = "2",
                            title = "Amazon",
                            amount = BigDecimal("-45.99"),
                            date = LocalDate.now(),
                            categoryName = "Home",
                            emoji = "🏠"
                        ),
                        DashboardComponent.TransactionInfo(
                            id = "3",
                            title = "Starbucks",
                            amount = BigDecimal("-7.50"),
                            date = LocalDate.now(),
                            categoryName = "Food & Groceries",
                            emoji = "🍕",
                            description = "Enjoyed a coffee and a snack at Starbucks with Rick and M."
                        )
                    )
                ),
                DashboardComponent.TransactionGroup(
                    title = "Yesterday",
                    transactions = listOf(
                        DashboardComponent.TransactionInfo(
                            id = "4",
                            title = "Rick's share - Birthday M.",
                            amount = BigDecimal("20.00"),
                            date = LocalDate.now().minusDays(1),
                            categoryName = "Income",
                            emoji = "💰"
                        ),
                        DashboardComponent.TransactionInfo(
                            id = "5",
                            title = "Netflix",
                            amount = BigDecimal("-15.99"),
                            date = LocalDate.now().minusDays(1),
                            categoryName = "Entertainment",
                            emoji = "🎬"
                        ),
                        DashboardComponent.TransactionInfo(
                            id = "6",
                            title = "Gym - Monthly Membership",
                            amount = BigDecimal("-45.99"),
                            date = LocalDate.now().minusDays(1),
                            categoryName = "Health",
                            emoji = "❤️"
                        )
                    )
                )
            )
        )
    }
}