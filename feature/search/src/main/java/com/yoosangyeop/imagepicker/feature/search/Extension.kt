package com.yoosangyeop.imagepicker.feature.search

import com.yoosangyeop.imagepicker.model.search.FavoriteDate
import com.yoosangyeop.imagepicker.model.search.SearchItem

fun List<SearchItem>.sortedByFavoriteDate(): List<SearchItem> =
    this.sortedWith(compareBy {
        if (it is FavoriteDate) {
            it.favoriteDate
        } else {
            0
        }
    })
