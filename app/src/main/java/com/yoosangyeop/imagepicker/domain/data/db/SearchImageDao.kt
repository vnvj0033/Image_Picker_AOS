package com.yoosangyeop.imagepicker.domain.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yoosangyeop.imagepicker.domain.data.model.SearchImage

@Dao
interface SearchImageDao {

    @Query("SELECT * FROM ImageDocument")
    fun getAll() : List<SearchImage.ImageDocument>

    @Insert
    fun insert(clip: SearchImage.ImageDocument)

    @Delete
    fun delete(clip: SearchImage.ImageDocument)
}