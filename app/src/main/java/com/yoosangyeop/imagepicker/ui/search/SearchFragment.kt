package com.yoosangyeop.imagepicker.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.yoosangyeop.imagepicker.databinding.FragmentSearchBinding
import com.yoosangyeop.imagepicker.util.SearchItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)
        initFragment(binding)
        return binding.root
    }

    private fun initFragment(binding: FragmentSearchBinding) = with(binding) {
        val viewModel: SearchViewModel by viewModels()

        val searchAdapter = SearchAdapter()

        itemRecyclerView.run {
            val spanCount = 2

            addItemDecoration(SearchItemDecoration(spanCount, 8, true))
            layoutManager = GridLayoutManager(context, spanCount)
            adapter = searchAdapter
        }

        searchEditText.setOnFocusChangeListener { view, b ->
            // TODO : 포커스 내려갈때 기록 숨기기 필요
            Log.d("testsyyooB", b.toString())
            if (b) {
                historyRecyclerView.isVisible
            } else {
                historyRecyclerView.isGone
            }
        }


        searchButton.setOnClickListener {
            val query = searchEditText.text ?: ""
            if (query.isNotEmpty()) {
                viewModel.search(query.toString())
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.query.collect { query ->
                Log.d("testsyyoo", "query : $query")
                // TODO: query 활용법 찾기(기능없음)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.searchHistory.collect { history ->
                Log.d("testsyyoo", "history : $history")
                // TODO: 검색 히스토리 UI 갱신
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.searchItem.collectLatest { items ->
                searchAdapter.submitData(items)
            }
        }
    }


}
