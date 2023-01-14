package com.yoosangyeop.imagepicker.domain.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class SearchClip(
    val meta: Meta,
    val documents: List<Document>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    data class Document(
        val title: String,
        val url: String,
        override val datetime: String,
        val play_time: Int,
        @SerializedName("thumbnail")
        override val thumbnail_url: String,
        val author: String
    ) : SearchItem
}

@Entity
data class SearchImage(
    val meta: Meta,
    val documents: List<Document>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    data class Document(
        val collection: String,
        override val thumbnail_url: String,
        val image_url: String,
        val width: Int,
        val height: Int,
        val display_sitename: String,
        val doc_url: String,
        override val datetime: String,
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
