package com.yoosangyeop.imagepicker.core.data.entrysource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yoosangyeop.imagepicker.model.search.SearchClip
import com.yoosangyeop.imagepicker.model.search.SearchImage

@Database(entities = [SearchImage.ImageDocument::class, SearchClip.ClipDocument::class], version = 1)
abstract class SearchFavoriteDatabase : RoomDatabase() {
    abstract fun searchImageDao(): SearchImageDao
    abstract fun searchClipDao(): SearchClipDao
}