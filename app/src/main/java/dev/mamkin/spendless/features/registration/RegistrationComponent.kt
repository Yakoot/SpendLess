package dev.mamkin.spendless.features.registration

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.mamkin.spendless.features.registration.newpin.NewPinComponent
import dev.mamkin.spendless.features.registration.newuser.NewUserComponent

interface RegistrationComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class NewUser(val component: NewUserComponent) : Child
        data class NewPin(val component: NewPinComponent) : Child
        data class RepeatPin(val component: NewPinComponent) : Child
    }
}
