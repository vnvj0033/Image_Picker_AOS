package com.yoosangyeop.imagepicker.data.model

import com.google.gson.annotations.SerializedName

data class Meta(
    val total_count: Int,
    val pageable_count: Int,
    val is_end: Boolean
)

data class SearchVClip(
    val meta: Meta,
    val documents: List<Document>
) {
    data class Document(
        val title: String,
        val play_time: Int,
        @SerializedName("thumbnail")
        override val thumbnail_url: String,
        val url: String,
        override val datetime: String,
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

interface SearchItem {
    val thumbnail_url: String
    val datetime: String
}

//{
//  "meta": {
//    "total_count": 422583,
//    "pageable_count": 3854,
//    "is_end": false
//  },
//  "documents": [
//    {
//      "collection": "news",
//      "thumbnail_url": "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp",
//      "image_url": "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg",
//      "width": 540,
//      "height": 457,
//      "display_sitename": "한국경제TV",
//      "doc_url": "http://v.media.daum.net/v/20170621155930002",
//      "datetime": "2017-06-21T15:59:30.000+09:00"
//    },
//    ...
//  ]
//}