package com.yoosangyeop.imagepicker.domain.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class SearchClip(
    val meta: Meta,
    val documents: List<ClipDocument>
) {
    @Entity
    data class ClipDocument(
        @PrimaryKey
        @SerializedName("thumbnail")
        override val thumbnail_url: String,
        override val datetime: String,
        val title: String,
        val url: String,
        val play_time: Int,
        val author: String
    ) : SearchItem
}

data class SearchImage(
    val meta: Meta,
    val documents: List<ImageDocument>
) {
    @Entity
    data class ImageDocument(
        @PrimaryKey
        override val thumbnail_url: String,
        override val datetime: String,
        val collection: String,
        val image_url: String,
        val width: Int,
        val height: Int,
        val display_sitename: String,
        val doc_url: String,
    ) : SearchItem
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