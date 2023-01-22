package com.yoosangyeop.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class SearchClip(
    val meta: Meta,
    val documents: List<ClipDocument>
) {
    @Entity
    data class ClipDocument(
        @SerializedName("thumbnail")
        override val thumbnail_url: String,
        override val datetime: String,
        val title: String,
        val url: String,
        val play_time: Int,
        val author: String
    ) : SearchItem, FavoriteDate {
        @PrimaryKey(autoGenerate = true)
        var id = 0
        override var favoriteDate: Long? = null
    }
}

data class SearchImage(
    val meta: Meta,
    val documents: List<ImageDocument>
) {
    @Entity
    data class ImageDocument(
        override val thumbnail_url: String,
        override val datetime: String,
        val collection: String,
        val image_url: String,
        val width: Int,
        val height: Int,
        val display_sitename: String,
        val doc_url: String,
    ) : SearchItem, FavoriteDate {
        @PrimaryKey(autoGenerate = true)
        var id = 0
        override var favoriteDate: Long? = null
    }
}

data class Meta(
    val total_count: Int,
    val pageable_count: Int,
    val is_end: Boolean
)


interface SearchItem {
    val thumbnail_url: String
    val datetime: String
}

interface FavoriteDate {
    var favoriteDate: Long?
}