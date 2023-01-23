package com.yoosangyeop.imagepicker.feature.search.util

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.StateFlow

internal class SavableMutableStateFlow<T>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    initialValue: T
) {
    private val state: StateFlow<T> = savedStateHandle.getStateFlow(key, initialValue)
    var value: T
        get() = state.value
        set(value) {
            savedStateHandle[key] = value
        }
    fun asStateFlow(): StateFlow<T> = state
}

internal fun <T> SavedStateHandle.getSavableMutableStateFlow(key: String, initialValue: T) =
    SavableMutableStateFlow(this, key, initialValue)