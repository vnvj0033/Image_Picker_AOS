package com.yoosangyeop.imagepicker.core.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yoosangyeop.imagepicker.core.data.api.SearchService
import com.yoosangyeop.imagepicker.model.search.SearchClip
import com.yoosangyeop.imagepicker.model.search.SearchImage
import com.yoosangyeop.imagepicker.model.search.SearchItem
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

internal class SearchItemPagingSource(
    private val searchService: SearchService,
    private val query: String
) : PagingSource<Int, SearchItem>() {

    companion object {
        private const val DEFAULT_START = 1
        private const val MAX_SIZE_OF_IMAGE_PAGE = 50
        private const val MAX_SIZE_OF_CLIP_PAGE = 15
    }

    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return state.anchorPosition?.let {
            val closestPageToPosition = state.closestPageToPosition(it)
            closestPageToPosition?.prevKey?.plus(1)
                ?: closestPageToPosition?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), null, null)
        }

        return try {
            LoadResult.Page(emptyList(), null, null)

            val start = params.key ?: DEFAULT_START

            val items = coroutineScope {
                val images = async { loadImages(params.loadSize, start) }
                val clips = async { loadClips(params.loadSize, start) }
                images.await() + clips.await()
            }
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

        if (images.meta.is_end || start >= MAX_SIZE_OF_IMAGE_PAGE) {
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

        if (clips.meta.is_end || start >= MAX_SIZE_OF_CLIP_PAGE) {
            isEndPageOfClips = true
        }

        return clips.documents
    }

}
