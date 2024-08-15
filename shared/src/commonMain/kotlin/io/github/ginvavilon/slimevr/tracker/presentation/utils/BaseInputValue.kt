package io.github.ginvavilon.slimevr.tracker.presentation.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseInputValue(defaultValue: String) : InputValue<String> {

    private val _state = MutableStateFlow(getState(defaultValue))

    override val value: String
        get() = _state.value.text

    override val state: StateFlow<InputValue.State> = _state.asStateFlow()

    override fun update(text: String) {
        _state.value = getState(text)
    }

    protected abstract fun getState(text: String): InputValue.State
}