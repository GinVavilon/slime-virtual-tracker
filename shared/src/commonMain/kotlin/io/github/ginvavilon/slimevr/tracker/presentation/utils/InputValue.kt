package io.github.ginvavilon.slimevr.tracker.presentation.utils

import io.github.ginvavilon.slimevr.tracker.presentation.utils.InputValue.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface InputValue<T> {

    val value: T

    val state: StateFlow<State>

    fun update(text: String)

    sealed interface State {
        val text: String

        data class Normal(override val text: String) : State

        data class Error(override val text: String, val error: String? = null) : State

    }


}

fun inputValueOf(defaultValue: String): InputValue<String> {

    return object : InputValue<String> {

        private val _state = MutableStateFlow<State>(State.Normal(defaultValue))

        override val value: String
            get() = _state.value.text

        override val state: StateFlow<State> = _state.asStateFlow()

        override fun update(text: String) {
            _state.value = State.Normal(text)
        }

    }
}

fun inputValueOf(defaultValue: Float) =
    inputValueOf(defaultValue, String::toFloatOrNull)

fun inputValueOf(defaultValue: Int) =
    inputValueOf(defaultValue, String::toIntOrNull)

fun <T> inputValueOf(
    defaultValue: T,
    transformation: (text: String) -> T?
): InputValue<T> {

    return object : InputValue<T> {
        private var _value: T? = defaultValue
        override val value: T
            get() = _value ?: throw IllegalStateException("Incorrect input value $state")

        private val _state = MutableStateFlow<State>(State.Normal(defaultValue.toString()))
        override val state: StateFlow<State> = _state.asStateFlow()


        override fun update(text: String) {
            val newValue = transformation(text)
            if (newValue == null) {
                _value = defaultValue
                _state.value = State.Error(text)
            } else {
                _value = newValue
                _state.value = State.Normal(text)
            }
        }


    }
}

fun inputValueOf(
    defaultValue: String,
    validator: (text: String) -> Boolean
): InputValue<String> {

    return object : BaseInputValue(defaultValue) {

        override fun getState(text: String) = if (validator(text)) {
            State.Normal(text)
        } else {
            State.Error(text)
        }

    }
}

fun inputValueOf(
    defaultValue: String,
    regex: Regex
): InputValue<String> {

    return object : BaseInputValue(defaultValue) {

        override fun getState(text: String) = if (regex.matches(text)) {
            State.Normal(text)
        } else {
            State.Error(text)
        }

    }
}