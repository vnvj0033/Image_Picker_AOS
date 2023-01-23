package com.yoosangyeop.imagepicker.feature.search.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yoosangyeop.imagepicker.model.search.FavoriteDate
import com.yoosangyeop.imagepicker.model.search.SearchItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal fun List<SearchItem>.sortedByFavoriteDate(): List<SearchItem> =
    this.sortedWith(compareBy {
        if (it is FavoriteDate) {
            it.favoriteDate
        } else {
            0
        }
    })

internal fun LifecycleOwner.launchWhenStart(block: suspend CoroutineScope.() -> Unit) = this.lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
        block.invoke(this)
    }
}