package com.yoosangyeop.imagepicker.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import androidx.appcompat.widget.AppCompatImageView


class PinchImageView : AppCompatImageView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var scaleFactor = 1.0f
    private val scaleGestureDetector = ScaleGestureDetector(context,
        object : SimpleOnScaleGestureListener() {
            override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
                scaleFactor *= scaleGestureDetector.scaleFactor
                scaleFactor = 0.25f.coerceAtLeast(scaleFactor.coerceAtMost(4.0f))

                scaleX = scaleFactor
                scaleY = scaleFactor
                return true
            }
        })

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        return true
    }


}