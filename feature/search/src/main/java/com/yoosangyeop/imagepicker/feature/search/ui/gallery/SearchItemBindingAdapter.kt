package com.yoosangyeop.imagepicker.feature.search.ui.gallery

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.yoosangyeop.imagepicker.feature.search.R
import com.yoosangyeop.imagepicker.feature.search.util.DateUtil
import com.yoosangyeop.imagepicker.model.search.SearchClip
import com.yoosangyeop.imagepicker.model.search.SearchImage
import com.yoosangyeop.imagepicker.model.search.SearchItem

object SearchItemBindingAdapter {
    @BindingAdapter("search:setIconType")
    @JvmStatic
    fun setIconType(view: ImageView, searchItem: SearchItem){
        when (searchItem) {
            is SearchImage.ImageDocument -> view.setImageResource(R.drawable.ic_image)
            is SearchClip.ClipDocument -> view.setImageResource(R.drawable.ic_video)
            else -> view.isGone
        }
    }

    @BindingAdapter("search:setDataTime")
    @JvmStatic
    fun setDataTime(view: TextView, searchItem: SearchItem) {
        val date = DateUtil.changeDatePattern(
            searchItem.datetime,
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            "yyyy.MM.dd\nHH:mm:ss"
        )
        view.text = date
    }

    @BindingAdapter("search:setThumbnail")
    @JvmStatic
    fun setThumbnail(view: ImageView, searchItem: SearchItem) {

        Glide.with(view.context)
            .load(searchItem.thumbnail_url)
            .into(view)
    }
}