package com.yoosangyeop.imagepicker.core.data.entrysource.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoosangyeop.imagepicker.core.data.model.SearchClip


@Dao
interface SearchClipDao {
    @Query("SELECT * FROM ClipDocument")
    fun getAll(): List<SearchClip.ClipDocument>

    @Insert
    fun insert(clip: SearchClip.ClipDocument)

    @Query("DELETE FROM ClipDocument WHERE thumbnail_url = :url AND datetime = :datetime")
    fun delete(url: String, datetime: String)
}
