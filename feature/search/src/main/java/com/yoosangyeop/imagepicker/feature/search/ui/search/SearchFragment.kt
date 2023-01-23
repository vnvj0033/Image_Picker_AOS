package com.yoosangyeop.imagepicker.feature.search.ui.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.yoosangyeop.imagepicker.feature.search.databinding.FragmentSearchBinding
import com.yoosangyeop.imagepicker.feature.search.ui.dialog.PinChImageDialogFragment
import com.yoosangyeop.imagepicker.feature.search.util.ListItemDecoration
import com.yoosangyeop.imagepicker.feature.search.util.launchWhenStart
import com.yoosangyeop.imagepicker.model.search.SearchClip
import com.yoosangyeop.imagepicker.model.search.SearchImage
import dagger.hilt.android.AndroidEntryPoint

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
        searchRecyclerView.run {
            addItemDecoration(ListItemDecoration(SEARCH_LIST_SPAN_COUNT, 8))
            layoutManager = GridLayoutManager(context, SEARCH_LIST_SPAN_COUNT)
            adapter = searchAdapter
        }

        historyRecyclerView.run {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(context)
        }

        searchAdapter.clickFavorite = viewModel::clickFavorite
        historyAdapter.clickRemove = viewModel::removeHistory

        searchAdapter.clickItem = { item ->
            if (item is SearchImage.ImageDocument) {
                PinChImageDialogFragment(item.image_url)
                    .show(parentFragmentManager, null)
            } else if (item is SearchClip.ClipDocument) {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(item.url)
                })
            }
        }


        historyAdapter.clickItem = { query ->
            searchEditText.setText(query)
            searchEditText.isFocusable = true
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
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, backPressedCallback)

        // editText focus, 최근 검색어 노출
        searchEditText.setOnFocusChangeListener { _, isFocus ->
            backPressedCallback.isEnabled = isFocus
            historyLayout.isVisible = isFocus
        }

        searchEditText.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                searchButton.callOnClick()
                return@setOnEditorActionListener false
            }
            return@setOnEditorActionListener true
        }
    }

    private fun initFlow() = with(viewLifecycleOwner.lifecycleScope) {

        launchWhenStart {
            viewModel.query.collect { query ->
                android.util.Log.d("testsyyoo", "query : $query")
            }
        }

        launchWhenStart {
            viewModel.searchHistory.collect { history ->
                historyAdapter.updateHistory(history)
            }
        }

        launchWhenStart {
            viewModel.favorite.collect { favorites ->
                // 즐겨찾기 갱신
                searchAdapter.updateFavorites(favorites)
            }
        }

        launchWhenStart {
            viewModel.searchItem.collect { items ->
                // 검색 결과 갱신
                searchAdapter.submitData(items)
            }
        }
    }

}