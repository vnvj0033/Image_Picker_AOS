package com.yoosangyeop.imagepicker.core.data

import com.yoosangyeop.imagepicker.core.data.model.SearchItem
import java.text.SimpleDateFormat
import java.util.*

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