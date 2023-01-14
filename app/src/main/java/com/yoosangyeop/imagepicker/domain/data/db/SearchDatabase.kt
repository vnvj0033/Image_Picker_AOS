package com.yoosangyeop.imagepicker.domain.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yoosangyeop.imagepicker.domain.data.model.SearchClip
import com.yoosangyeop.imagepicker.domain.data.model.SearchImage

@Database(entities = [SearchImage::class, SearchClip::class], version = 1)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun searchImageDao(): SearchImageDao
    abstract fun searchClipDao(): SearchClipDao
}