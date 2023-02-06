package com.yoosangyeop.imagepicker.model

import com.yoosangyeop.imagepicker.model.search.FavoriteDate
import com.yoosangyeop.imagepicker.model.search.SearchItem
import java.text.SimpleDateFormat
import java.util.*

fun List<SearchItem>.sortByNewest() {
    val comparator = Comparator<SearchItem> { o1, o2 ->
        runCatching {
            val date1 = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                Locale.getDefault()
            ).parse(o1.datetime)
            val date2 = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                Locale.getDefault()
            ).parse(o2.datetime)
            return@Comparator date2!!.compareTo(date1)
        }
        return@Comparator 0
    }
    Collections.sort(this, comparator)
}

fun List<SearchItem>.sortedByFavoriteDate(isReverse: Boolean = false): List<SearchItem> =
    this.sortedBy {
        if (it is FavoriteDate) {
            (it as FavoriteDate).favoriteDate
        } else {
            0
        }
    }.let { sortedList ->
        if (isReverse) {
            sortedList.asReversed()
        } else {
            sortedList
        }
    }