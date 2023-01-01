package com.yoosangyeop.imagepicker.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.yoosangyeop.imagepicker.domain.SearchRepository
import com.yoosangyeop.imagepicker.getMutableStateFlow
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

    private val _searchHistory = MutableSharedFlow<List<String>>(replay = 1)
    val searchHistory: SharedFlow<List<String>> = _searchHistory.asSharedFlow()

    private val _query = savedStateHandle.getMutableStateFlow(KEY_NAME_QUERY, "")
    val query: StateFlow<String>
        get() = _query.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchItem = query.flatMapLatest {
        searchRepository.loadSearchItem(_query.value)
    }.cachedIn(viewModelScope)


    private fun addHistory() {
        val addQuery = _query.value

        viewModelScope.launch {
            searchRepository.removeHistory(addQuery)
            searchRepository.addHistory(addQuery)

            searchRepository.searchHistory.collect { history ->
                _searchHistory.emit(history)
            }
        }
    }

    fun search(searchQuery: String) {
        _query.value = searchQuery
        addHistory()
    }
}