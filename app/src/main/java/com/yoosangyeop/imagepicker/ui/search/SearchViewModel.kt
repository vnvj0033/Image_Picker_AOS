package com.yoosangyeop.imagepicker.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.yoosangyeop.imagepicker.domain.SearchRepository
import com.yoosangyeop.imagepicker.util.getSavableMutableStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val KEY_NAME_QUERY = "KEY_NAME_QUERY"

@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchHistory = MutableStateFlow(searchRepository.searchHistory)
    val searchHistory = _searchHistory.asStateFlow()

    private val _favorite = MutableStateFlow(searchRepository.favorite)
    val favorite = _favorite.asStateFlow()

    private val _query = savedStateHandle.getSavableMutableStateFlow(KEY_NAME_QUERY, "")
    val query = _query.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchItem = query.flatMapLatest {
        searchRepository.loadSearchItem(_query.value).flow
    }.cachedIn(viewModelScope)

    fun search(searchQuery: String) {
        _query.value = searchQuery
        addHistory()

        viewModelScope.launch {
            _favorite.value = searchRepository.favorite
        }
    }

    fun clickFavorite(url: String) {
        viewModelScope.launch {
            if (_favorite.value.contains(url)) {
                searchRepository.removeFavorite(url)
            } else {
                searchRepository.addFavorite(url)
            }
            _favorite.value = searchRepository.favorite
        }
    }

    private fun addHistory() {
        val addQuery = _query.value
        viewModelScope.launch {
            searchRepository.removeHistory(addQuery)
            searchRepository.addHistory(addQuery)

            _searchHistory.value = searchRepository.searchHistory.reversed()
        }
    }

    fun removeHistory(query: String) {
        viewModelScope.launch {
            searchRepository.removeHistory(query)
            _searchHistory.value = searchRepository.searchHistory
        }
    }
}