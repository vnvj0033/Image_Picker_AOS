package com.yoosangyeop.imagepicker.feature.search.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal fun LifecycleOwner.launchWhenStart(block: suspend CoroutineScope.() -> Unit) = this.lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
        block.invoke(this)
    }
}