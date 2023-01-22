package com.yoosangyeop.imagepicker.domain.datasource

import com.yoosangyeop.core.data.model.SearchClip
import com.yoosangyeop.core.data.model.SearchImage
import com.yoosangyeop.core.data.model.SearchItem
import com.yoosangyeop.imagepicker.domain.data.db.SearchClipDao
import com.yoosangyeop.imagepicker.domain.data.db.SearchImageDao
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
        else -> check(true)
    }

    fun removeFavorite(item: SearchItem) = when (item) {
        is SearchImage.ImageDocument -> imageDao.delete(item.thumbnail_url, item.datetime)
        is SearchClip.ClipDocument -> clipDao.delete(item.thumbnail_url, item.datetime)
        else -> check(true)
    }
}
