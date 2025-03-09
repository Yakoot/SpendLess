package dev.mamkin.spendless.features.dashboard

import kotlinx.coroutines.flow.StateFlow
import java.math.BigDecimal
import java.time.LocalDate

interface DashboardComponent {
    val state: StateFlow<State>

    data class State(
        val username: String = "testtest",
        val accountBalance: BigDecimal = BigDecimal.ZERO,
        val popularCategory: CategoryInfo? = null,
        val largestTransaction: TransactionInfo? = null,
        val previousWeekSpending: BigDecimal = BigDecimal.ZERO,
        val transactions: List<TransactionGroup> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    data class CategoryInfo(
        val name: String,
        val description: String,
        val emoji: String = ""
    )

    data class TransactionInfo(
        val id: String,
        val title: String,
        val amount: BigDecimal,
        val date: LocalDate,
        val description: String? = null,
        val categoryName: String? = null,
        val emoji: String = ""
    )

    data class TransactionGroup(
        val title: String,
        val transactions: List<TransactionInfo>
    )

    fun onEvent(event: UiEvent)

    sealed interface UiEvent {
        object RefreshData : UiEvent
        object NavigateToSettings : UiEvent
        object NavigateToAddTransaction : UiEvent
        data class TransactionClicked(val transactionId: String) : UiEvent
        data class CategoryClicked(val categoryName: String) : UiEvent
    }
}
