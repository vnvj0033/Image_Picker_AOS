package com.yoosangyeop.imagepicker.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yoosangyeop.imagepicker.databinding.FragmentSearchBinding
import com.yoosangyeop.imagepicker.util.SearchItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

private const val SEARCH_LIST_SPAN_COUNT = 3

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels()
    private val searchAdapter = SearchAdapter()
    private val historyAdapter = HistoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
        initFlow()
    }

    private fun initView() = with(binding) {
        itemRecyclerView.run {
            addItemDecoration(SearchItemDecoration(SEARCH_LIST_SPAN_COUNT, 8, true))
            layoutManager = GridLayoutManager(context, SEARCH_LIST_SPAN_COUNT)
            adapter = searchAdapter
        }

        historyRecyclerView.adapter = historyAdapter
        historyRecyclerView.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launchWhenStarted {
            viewModel.emitHistory()
        }
    }

    private fun initEvent() = with(binding) {
        // 검색 클릭
        searchButton.setOnClickListener {
            val query = searchEditText.text ?: ""
            if (query.isNotEmpty()) {
                viewModel.search(query.toString())
                searchEditText.clearFocus()
            }
        }

        // 뒤로가기 클릭
        val backPressedCallback = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                if (searchEditText.isFocusable) {
                    searchEditText.clearFocus()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)

        searchEditText.setOnFocusChangeListener { _, b ->
            backPressedCallback.isEnabled = b
            historyLayout.isVisible = b
        }

        searchAdapter.clickFavorite = { url ->
            viewModel.clickFavorite(url)
        }

        historyAdapter.run {
            clickRemove = { query ->
                viewModel.removeHistory(query)
            }

            clickItem = { query ->
                searchEditText.setText(query)
                searchEditText.isFocusable = true
            }
        }


        searchEditText.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                searchButton.callOnClick()
                return@setOnEditorActionListener false
            }
            return@setOnEditorActionListener true
        }
    }

    private fun initFlow() = with(lifecycleScope) {

        launchWhenStarted {
            viewModel.query.collect { query ->
                Log.d("testsyyoo", "query : $query")
                // TODO: query 활용법 찾기(기능없음)
            }
        }

        launchWhenStarted {
            viewModel.searchHistory.collect { history ->
                historyAdapter.updateHistory(history)
            }
        }

        launchWhenStarted {
            viewModel.favorite.collect { favorites->
                // 즐겨찾기 갱신
                searchAdapter.updateFavorites(favorites)
            }
        }

        launchWhenStarted {
            viewModel.searchItem.collectLatest { items ->
                // 검색 결과 갱신
                searchAdapter.submitData(items)
                binding.itemRecyclerView.smoothScrollToPosition(0)
            }
        }
    }

}
