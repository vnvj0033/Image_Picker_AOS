package com.yoosangyeop.imagepicker.domain.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yoosangyeop.imagepicker.domain.data.model.SearchClip

@Dao
interface SearchClipDao {
    @Query("SELECT * FROM ClipDocument")
    fun getAll(): List<SearchClip.ClipDocument>

    @Insert
    fun insert(clip: SearchClip.ClipDocument)

    @Delete
    fun delete(clip: SearchClip.ClipDocument)
}