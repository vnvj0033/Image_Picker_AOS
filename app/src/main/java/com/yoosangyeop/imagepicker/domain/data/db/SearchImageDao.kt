package com.yoosangyeop.imagepicker.domain.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoosangyeop.imagepicker.domain.data.model.SearchImage

@Dao
interface SearchImageDao {

    @Query("SELECT * FROM SearchImage")
    fun getAll() : List<SearchImage>

    @Insert
    fun insert(clip: SearchImage)
}