package com.yoosangyeop.imagepicker.util

import android.util.TypedValue
import com.yoosangyeop.imagepicker.App

fun Int.dp() = this.toFloat().dp()
fun Float.dp() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, App.context.applicationContext.resources.displayMetrics)