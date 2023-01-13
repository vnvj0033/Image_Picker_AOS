package com.yoosangyeop.imagepicker.domain.data.model

import com.google.gson.annotations.SerializedName

data class SearchClip(
    val meta: Meta,
    val documents: List<Document>
) {
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

data class SearchImage(
    val meta: Meta,
    val documents: List<Document>
) {
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
