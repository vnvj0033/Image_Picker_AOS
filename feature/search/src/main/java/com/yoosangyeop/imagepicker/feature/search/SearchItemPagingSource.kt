package com.yoosangyeop.imagepicker.feature.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yoosangyeop.imagepicker.core.data.entrysource.api.SearchService
import com.yoosangyeop.imagepicker.core.data.sortByNewest
import com.yoosangyeop.imagepicker.model.search.SearchClip
import com.yoosangyeop.imagepicker.model.search.SearchImage
import com.yoosangyeop.imagepicker.model.search.SearchItem

class SearchItemPagingSource(
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

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> = try{
        val start = params.key ?: DEFAULT_START

        val images = loadImages(params.loadSize, start)
        val clips = loadClips(params.loadSize, start)

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

        LoadResult.Page(items, prevKey, nextKey)
    } catch (e: Exception) {
        LoadResult.Error(e)
    }


    private var isEndPageOfImages = false
    private var isEndPageOfClips = false
    private suspend fun loadImages(loadSize: Int, start: Int): List<SearchImage.ImageDocument> {
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

    private suspend fun loadClips(loadSize: Int, start: Int): List<SearchClip.ClipDocument> {
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
