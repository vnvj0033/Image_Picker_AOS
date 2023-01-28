package com.yoosangyeop.imagepicker.core.repository

import com.yoosangyeop.imagepicker.model.search.FavoriteDate
import com.yoosangyeop.imagepicker.model.search.SearchItem
import java.text.SimpleDateFormat
import java.util.*

internal fun List<SearchItem>.sortByNewest() {
    val comparator = Comparator<SearchItem> { o1, o2 ->
        runCatching {
            val date1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                Locale.getDefault()).parse(o1.datetime)
            val date2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                Locale.getDefault()).parse(o2.datetime)
            return@Comparator date2!!.compareTo(date1)
        }
        return@Comparator 0
    }
    Collections.sort(this, comparator)
}

internal fun List<SearchItem>.sortedByFavoriteDate(): List<SearchItem> =
    this.sortedWith(compareBy {
        if (it is FavoriteDate) {
            it.favoriteDate
        } else {
            0
        }
    })