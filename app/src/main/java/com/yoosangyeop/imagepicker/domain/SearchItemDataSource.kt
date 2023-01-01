package com.yoosangyeop.imagepicker.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yoosangyeop.imagepicker.data.api.SearchService
import com.yoosangyeop.imagepicker.data.model.SearchImage
import com.yoosangyeop.imagepicker.data.model.SearchItem
import com.yoosangyeop.imagepicker.data.model.SearchVClip

class SearchItemDataSource(
    private val searchService: SearchService,
    private val query: String
) : PagingSource<Int, SearchItem>() {

    companion object {
        private const val DEFAULT_START = 1
        const val DEFAULT_DISPLAY = 20
    }

    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return state.anchorPosition?.let {
            val closestPageToPosition = state.closestPageToPosition(it)
            closestPageToPosition?.prevKey?.plus(DEFAULT_DISPLAY)
                ?: closestPageToPosition?.nextKey?.minus(DEFAULT_DISPLAY)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        val start = params.key ?: DEFAULT_START

        return try {

            val images = loadImages(params.loadSize, start)
            val clips = loadClips(params.loadSize, start)

            val nextKey = if (images.isEmpty() && clips.isEmpty()) {
                null
            } else {
                start + 1
            }

            val prevKey = if (start == DEFAULT_START) {
                null
            } else {
                start - 1
            }

            val items = images + clips
            // TODO: itmes 를 datetime 기준을 정렬

            LoadResult.Page(items, prevKey, nextKey)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }


    var endPageOfImages = Int.MAX_VALUE
    var endPageOfClips = Int.MAX_VALUE
    private suspend fun loadImages(loadSize: Int, start: Int): List<SearchImage.Document> {
        if (endPageOfImages > start) return listOf()

        val images = searchService.getImages(
            query = query,
            size = loadSize,
            page = start
        )

        if (images.meta.is_end) {
            endPageOfImages = start
        }

        return images.documents
    }

    private suspend fun loadClips(loadSize: Int, start: Int): List<SearchVClip.Document> {
        if (endPageOfClips > start) return listOf()
        val clips = searchService.getVClips(
            query = query,
            page = loadSize,
            size = start,
        )

        if (clips.meta.is_end) {
            endPageOfClips = start
        }
        return clips.documents
    }

}
