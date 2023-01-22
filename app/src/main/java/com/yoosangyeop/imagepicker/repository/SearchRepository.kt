package com.yoosangyeop.imagepicker.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.yoosangyeop.imagepicker.core.data.datasource.FavoriteDataSource
import com.yoosangyeop.imagepicker.core.data.datasource.HistoryDataSource
import com.yoosangyeop.imagepicker.core.data.datasource.SearchItemDataSource
import com.yoosangyeop.imagepicker.core.data.entrysource.api.SearchService
import com.yoosangyeop.imagepicker.core.data.model.SearchItem
import com.yoosangyeop.imagepicker.util.sortedByFavoriteDate
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val historyDataSource: HistoryDataSource,
    private val favoriteDataSource: FavoriteDataSource,
    private val searchService: SearchService
) : SearchRepository {
    override val searchHistory: List<String>
        get() = historyDataSource.loadHistory()

    override val favorite: List<SearchItem>
        get() = favoriteDataSource.loadFavorites().sortedByFavoriteDate()

    override suspend fun addHistory(query: String) {
        val list = searchHistory + query
        historyDataSource.setHistory(list)
    }

    override suspend fun removeHistory(query: String) {
        val list = searchHistory - query
        historyDataSource.setHistory(list)
    }

    override suspend fun addFavorite(item: SearchItem) = favoriteDataSource.addFavorite(item)
    override suspend fun removeFavorite(item: SearchItem) = favoriteDataSource.removeFavorite(item)

    override fun loadSearchItem(query: String): Pager<Int, SearchItem> {
        return Pager(
            config = PagingConfig(
                pageSize = SearchItemDataSource.DEFAULT_DISPLAY,
                enablePlaceholders = false,
                initialLoadSize = SearchItemDataSource.DEFAULT_DISPLAY,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED
            ),
            pagingSourceFactory = {
                SearchItemDataSource(searchService, query)
            }
        )
    }
}

interface SearchRepository {
    val searchHistory: List<String>
    suspend fun addHistory(query: String)
    suspend fun removeHistory(query: String)

    val favorite: List<SearchItem>
    suspend fun addFavorite(item: SearchItem)
    suspend fun removeFavorite(item: SearchItem)

    fun loadSearchItem(query: String): Pager<Int, SearchItem>
}

