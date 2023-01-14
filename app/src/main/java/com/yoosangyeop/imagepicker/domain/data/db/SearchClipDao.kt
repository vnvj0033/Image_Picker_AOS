package com.yoosangyeop.imagepicker.domain.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yoosangyeop.imagepicker.domain.data.model.SearchClip

@Dao
interface SearchClipDao {
    @Query("SELECT * FROM SearchClip")
    fun getAll(): List<SearchClip>

    @Insert
    fun insert(clip: SearchClip)
}
