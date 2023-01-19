package com.yoosangyeop.imagepicker.util

import android.util.TypedValue
import androidx.lifecycle.*
import com.yoosangyeop.imagepicker.App
import com.yoosangyeop.imagepicker.domain.data.model.FavoriteDate
import com.yoosangyeop.imagepicker.domain.data.model.SearchItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator

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


fun List<SearchItem>.sortByNewest() {
    val comparator = Comparator<SearchItem> { o1, o2 ->
        runCatching {
            val date1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                Locale.KOREA).parse(o1.datetime)
            val date2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                Locale.KOREA).parse(o2.datetime)
            return@Comparator date2!!.compareTo(date1)
        }
        return@Comparator 0
    }
    Collections.sort(this, comparator)
}