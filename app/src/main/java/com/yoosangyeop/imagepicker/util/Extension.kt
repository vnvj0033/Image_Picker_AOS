package com.yoosangyeop.imagepicker.util

import android.util.TypedValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yoosangyeop.imagepicker.App
import com.yoosangyeop.imagepicker.core.data.model.FavoriteDate
import com.yoosangyeop.imagepicker.core.data.model.SearchItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Int.dp() = this.toFloat().dp()
fun Float.dp() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, App.context.resources.displayMetrics)

fun LifecycleOwner.launchWhenStart(block: suspend CoroutineScope.() -> Unit) = this.lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
        block.invoke(this)
    }
}


fun List<SearchItem>.sortedByFavoriteDate(): List<SearchItem> =
    this.sortedWith(compareBy {
        if (it is FavoriteDate) {
            it.favoriteDate
        } else {
            0
        }
    })
