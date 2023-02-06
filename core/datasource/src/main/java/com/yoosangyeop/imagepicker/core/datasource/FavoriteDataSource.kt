package com.yoosangyeop.imagepicker.core.datasource

import com.yoosangyeop.imagepicker.core.data.db.SearchClipDao
import com.yoosangyeop.imagepicker.core.data.db.SearchImageDao
import com.yoosangyeop.imagepicker.model.search.SearchClip
import com.yoosangyeop.imagepicker.model.search.SearchImage
import com.yoosangyeop.imagepicker.model.search.SearchItem
import javax.inject.Inject


class FavoriteDataSource @Inject constructor(
    private val clipDao: SearchClipDao,
    private val imageDao: SearchImageDao,
) {
    fun loadFavorites(): List<SearchItem> {
        val images = imageDao.getAll()
        val clips = clipDao.getAll()

        return images + clips
    }

    fun addFavorite(item: SearchItem) = when (item) {
        is SearchImage.ImageDocument -> imageDao.insert(item)
        is SearchClip.ClipDocument -> clipDao.insert(item)
        else -> check(false)
    }

    fun removeFavorite(item: SearchItem) = when (item) {
        is SearchImage.ImageDocument -> imageDao.delete(item.thumbnail_url, item.datetime)
        is SearchClip.ClipDocument -> clipDao.delete(item.thumbnail_url, item.datetime)
        else -> check(false)
    }
}
