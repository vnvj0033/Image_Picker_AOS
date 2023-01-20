package com.yoosangyeop.imagepicker.ui.gallery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.yoosangyeop.imagepicker.databinding.FragmentGalleryBinding
import com.yoosangyeop.imagepicker.domain.data.model.SearchClip
import com.yoosangyeop.imagepicker.domain.data.model.SearchImage
import com.yoosangyeop.imagepicker.ui.dialog.PinChImageDialogFragment
import com.yoosangyeop.imagepicker.ui.search.SearchViewModel
import com.yoosangyeop.imagepicker.util.ListItemDecoration
import com.yoosangyeop.imagepicker.util.launchWhenStart
import dagger.hilt.android.AndroidEntryPoint

private const val FAVORITE_LIST_SPAN_COUNT = 2

@AndroidEntryPoint
class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels()
    private val favoriteAdapter = FavoriteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.itemRecyclerView.run {
            addItemDecoration(ListItemDecoration(FAVORITE_LIST_SPAN_COUNT, 8))
            layoutManager = GridLayoutManager(context, FAVORITE_LIST_SPAN_COUNT)
            adapter = favoriteAdapter

        }

        favoriteAdapter.clickRemove = viewModel::clickFavorite

        favoriteAdapter.clickItem = { item ->
            if (item is SearchImage.ImageDocument) {
                PinChImageDialogFragment(item.image_url)
                    .show(parentFragmentManager, null)
            } else if (item is SearchClip.ClipDocument) {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(item.url)
                })
            }
        }

        launchWhenStart {
            viewModel.favorite.collect { favorites ->
                favoriteAdapter.updateFavorites(favorites)
            }
        }
    }
}