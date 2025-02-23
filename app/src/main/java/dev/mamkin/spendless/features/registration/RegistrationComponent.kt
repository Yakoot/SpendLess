package dev.mamkin.spendless.features.registration

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.mamkin.spendless.features.pincode.PinCodeComponent
import dev.mamkin.spendless.features.registration.newuser.NewUserComponent
import dev.mamkin.spendless.features.registration.preferences.PreferencesComponent
import kotlinx.coroutines.flow.StateFlow

interface RegistrationComponent {
    val stack: Value<ChildStack<*, Child>>
    val error: StateFlow<String?>

    sealed interface Child {
        data class NewUser(val component: NewUserComponent) : Child
        data class NewPin(val component: PinCodeComponent) : Child
        data class RepeatPin(val component: PinCodeComponent) : Child
        data class Preferences(val component: PreferencesComponent) : Child
    }
}
