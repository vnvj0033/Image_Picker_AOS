package com.yoosangyeop.imagepicker.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yoosangyeop.imagepicker.data.api.SearchService
import com.yoosangyeop.imagepicker.data.model.SearchImage
import com.yoosangyeop.imagepicker.data.model.SearchItem
import com.yoosangyeop.imagepicker.data.model.SearchVClip
import com.yoosangyeop.imagepicker.util.DateUtil.sortByNewest

class SearchItemDataSource(
    private val searchService: SearchService,
    private val query: String
) : PagingSource<Int, SearchItem>() {

    companion object {
        private const val DEFAULT_START = 1
        const val DEFAULT_DISPLAY = 30
    }

    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return state.anchorPosition?.let {
            val closestPageToPosition = state.closestPageToPosition(it)
            closestPageToPosition?.prevKey?.plus(1)
                ?: closestPageToPosition?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        val start = params.key ?: DEFAULT_START

        val images = runCatching { loadImages(params.loadSize, start) }.getOrNull() ?: listOf()
        val clips = runCatching { loadClips(params.loadSize, start) }.getOrNull() ?: listOf()

        val items = images + clips
        items.sortByNewest()

        val nextKey =
            if (items.isEmpty()) {
                null
            } else {
                start + 1
            }

        val prevKey =
            if (start == DEFAULT_START) {
                null
            } else {
                start - 1
            }

        return LoadResult.Page(items, prevKey, nextKey)
    }


    private var isEndPageOfImages = false
    private var isEndPageOfClips = false
    private suspend fun loadImages(loadSize: Int, start: Int): List<SearchImage.Document> {
        if (isEndPageOfImages) return listOf()

        val images = searchService.getImages(
            query = query,
            sort = "recency",
            size = loadSize,
            page = start
        )

        if (images.meta.is_end || start > 50) {
            isEndPageOfImages = true
        }

        return images.documents
    }

    private suspend fun loadClips(loadSize: Int, start: Int): List<SearchVClip.Document> {
        if (isEndPageOfClips) return listOf()
        val clips = searchService.getVClips(
            query = query,
            sort = "recency",
            size = loadSize,
            page = start,
        )

        if (clips.meta.is_end || start > 15) {
            isEndPageOfClips = true
        }

        return clips.documents
    }

}
