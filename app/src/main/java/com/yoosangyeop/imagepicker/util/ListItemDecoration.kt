package com.yoosangyeop.imagepicker.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

private const val DEFAULT_SPAN_COUNT = 2
private const val DEFAULT_SPACING = 4

class ListItemDecoration(
    private val spanCount: Int = DEFAULT_SPAN_COUNT,
    private val spacing: Int = DEFAULT_SPACING,
    private val includeEdge: Boolean = true
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right =(column + 1) * spacing / spanCount

            if (position < spanCount)
                outRect.top = spacing

            outRect.bottom = spacing
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount)
                outRect.top = spacing
        }
    }
}