package com.yoosangyeop.imagepicker.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yoosangyeop.imagepicker.data.api.SearchService
import com.yoosangyeop.imagepicker.data.model.SearchItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val historyDataSource: HistoryDataSource,
    private val favoriteDataSource: FavoriteDataSource,
    private val searchService: SearchService
) : SearchRepository {
    override val searchHistory: Flow<List<String>>
        get() = historyDataSource.loadHistory()

    override suspend fun addHistory(query: String) {
        searchHistory.collect { history ->
            val list = history + query
            historyDataSource.setHistory(list)
        }
    }

    override suspend fun removeHistory(query: String) {
        searchHistory.collect { history ->
            val list = history - query
            historyDataSource.setHistory(list)
        }
    }

    override val favorite: Flow<List<String>>
        get() = favoriteDataSource.loadFavorites()

    override suspend fun addFavorite(url: String) {
        favorite.collect { favorite ->
            val list = favorite + url
            favoriteDataSource.setFavorites(list)
        }
    }

    override suspend fun removeFavorite(url: String) {
        favorite.collect { favorite ->
            val list = favorite - url
            favoriteDataSource.setFavorites(list)
        }
    }

    override fun loadSearchItem(query: String): Flow<PagingData<SearchItem>> {
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
        ).flow
    }

}


interface SearchRepository {
    val searchHistory: Flow<List<String>>
    suspend fun addHistory(query: String)
    suspend fun removeHistory(query: String)

    val favorite: Flow<List<String>>
    suspend fun addFavorite(url: String)
    suspend fun removeFavorite(url: String)

    fun loadSearchItem(query: String): Flow<PagingData<SearchItem>>
}

