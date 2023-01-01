package com.yoosangyeop.imagepicker.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yoosangyeop.imagepicker.data.api.SearchService
import com.yoosangyeop.imagepicker.data.model.SearchItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val historyDataSource: HistoryDataSource,
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

    override fun loadSearchItem(query: String): Flow<PagingData<SearchItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = SearchItemDataSource.DEFAULT_DISPLAY,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                SearchItemDataSource(searchService, query)
            }
        ).flow
    }

}

class FakeSearchRepository @Inject constructor(
    private val searchService: SearchService
) : SearchRepository {
    private var history = arrayListOf<String>()
    private var items = arrayListOf<String>()

    override suspend fun addHistory(query: String) {
        history.add(query)
    }
    override suspend fun removeHistory(query: String) {
        history.remove(query)
    }

    override fun loadSearchItem(query: String): Flow<PagingData<SearchItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = SearchItemDataSource.DEFAULT_DISPLAY,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchItemDataSource(searchService, query)
            }
        ).flow
    }

    override val searchHistory: Flow<List<String>> = flowOf(history)
}


interface SearchRepository {
    val searchHistory: Flow<List<String>>
    suspend fun addHistory(query: String)
    suspend fun removeHistory(query: String)

//    val searchItem: Flow<List<SearchItem>>
    fun loadSearchItem(query: String): Flow<PagingData<SearchItem>>
}

