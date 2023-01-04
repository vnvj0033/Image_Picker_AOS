package com.yoosangyeop.imagepicker.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.yoosangyeop.imagepicker.data.api.SearchService
import com.yoosangyeop.imagepicker.data.model.SearchItem
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val historyDataSource: HistoryDataSource,
    private val favoriteDataSource: FavoriteDataSource,
    private val searchService: SearchService
) : SearchRepository {
    override val searchHistory: List<String>
        get() = historyDataSource.loadHistory()

    override val favorite: List<String>
        get() = favoriteDataSource.loadFavorites()

    override suspend fun addHistory(query: String) {
        val list = searchHistory + query
        historyDataSource.setHistory(list)
    }

    override suspend fun removeHistory(query: String) {
        val list = searchHistory - query
        historyDataSource.setHistory(list)
    }

    override suspend fun addFavorite(url: String) {
        val list = favorite + url
        favoriteDataSource.setFavorites(list)
    }

    override suspend fun removeFavorite(url: String) {
        val list = favorite - url
        favoriteDataSource.setFavorites(list)
    }

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

    val favorite: List<String>
    suspend fun addFavorite(url: String)
    suspend fun removeFavorite(url: String)

    fun loadSearchItem(query: String): Pager<Int, SearchItem>
}

