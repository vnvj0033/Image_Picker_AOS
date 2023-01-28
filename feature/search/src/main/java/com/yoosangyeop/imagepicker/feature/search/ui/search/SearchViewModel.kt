package com.yoosangyeop.imagepicker.feature.search.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.yoosangyeop.imagepicker.core.repository.SearchRepository
import com.yoosangyeop.imagepicker.feature.search.util.getSavableMutableStateFlow
import com.yoosangyeop.imagepicker.model.search.FavoriteDate
import com.yoosangyeop.imagepicker.model.search.SearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val KEY_NAME_QUERY = "KEY_NAME_QUERY"

@HiltViewModel
internal class SearchViewModel @Inject constructor(
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
        searchRepository.loadSearchItem(it).flow
    }.cachedIn(viewModelScope)

    fun search(searchQuery: String) {
        _query.value = searchQuery
        addHistory()

        viewModelScope.launch {
            _favorite.value = searchRepository.favorite
        }
    }

    fun clickFavorite(favorite: SearchItem) {
        viewModelScope.launch {
            if (_favorite.value.contains(favorite)) {
                searchRepository.removeFavorite(favorite)
            } else {
                if (favorite is FavoriteDate) {
                    favorite.favoriteDate = System.currentTimeMillis()
                }
                searchRepository.addFavorite(favorite)
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