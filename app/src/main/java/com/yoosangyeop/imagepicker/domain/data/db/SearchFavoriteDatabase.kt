package com.yoosangyeop.imagepicker.domain.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yoosangyeop.core.data.model.SearchClip
import com.yoosangyeop.core.data.model.SearchImage

@Database(entities = [SearchImage.ImageDocument::class, SearchClip.ClipDocument::class], version = 1)
abstract class SearchFavoriteDatabase : RoomDatabase() {
    abstract fun searchImageDao(): SearchImageDao
    abstract fun searchClipDao(): SearchClipDao
}