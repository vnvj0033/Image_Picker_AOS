package com.yoosangyeop.imagepicker.data.api

import com.yoosangyeop.imagepicker.data.model.SearchImage
import com.yoosangyeop.imagepicker.data.model.SearchVClip
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchService {

    @GET("/v2/search/image")
    suspend fun getImages(
        @Query("query") query: String,
        @Query("sort") sort: String? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ) : SearchImage

    @GET("/v2/search/vclip")
    suspend fun getVClips(
        @Query("query") query: String,
        @Query("sort") sort: String? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ) : SearchVClip

}