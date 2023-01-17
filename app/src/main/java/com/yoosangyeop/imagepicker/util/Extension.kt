package com.yoosangyeop.imagepicker.util

import android.util.TypedValue
import androidx.lifecycle.*
import com.yoosangyeop.imagepicker.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Int.dp() = this.toFloat().dp()
fun Float.dp() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, App.context.resources.displayMetrics)

fun LifecycleOwner.launchWhenStart(block: suspend CoroutineScope.() -> Unit) = this.lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
        block.invoke(this)
    }
}