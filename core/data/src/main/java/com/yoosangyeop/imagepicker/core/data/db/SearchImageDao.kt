package com.yoosangyeop.imagepicker.core.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoosangyeop.imagepicker.model.search.SearchImage

@Dao
interface SearchImageDao {

    @Query("SELECT * FROM ImageDocument")
    fun getAll() : List<SearchImage.ImageDocument>

    @Insert
    fun insert(clip: SearchImage.ImageDocument)

    @Query("DELETE FROM ImageDocument WHERE thumbnail_url = :url AND datetime = :datetime")
    fun delete(url: String, datetime: String)
}