package com.yoosangyeop.imagepicker.feature.search.util

import java.text.SimpleDateFormat
import java.util.*

internal object DateUtil {

    fun changeDatePattern(date: String, fromPattern: String, toPattern: String): String {
        return runCatching {
            val format = SimpleDateFormat(fromPattern, Locale.KOREA)
            SimpleDateFormat(toPattern, Locale.KOREA).format(format.parse(date)!!)
        }.getOrNull() ?: date
    }
}