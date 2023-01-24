package com.yoosangyeop.imagepicker.feature.search.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yoosangyeop.imagepicker.feature.search.R
import com.yoosangyeop.imagepicker.feature.search.databinding.ItemSearchBinding
import com.yoosangyeop.imagepicker.model.search.SearchItem

internal class SearchAdapter : PagingDataAdapter<SearchItem, SearchAdapter.SearchItemViewHolder>(comparator) {
    var clickFavorite: ((SearchItem) -> Unit)? = null
    var clickItem: ((SearchItem) -> Unit)? = null
    private var favorites: List<SearchItem> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(layoutInflater, parent, false)
        return SearchItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    fun updateFavorites(newFavorites: List<SearchItem>) {
        val favorite = if (newFavorites.size > favorites.size) {
            newFavorites - favorites.toSet()
        } else if (favorites.size > newFavorites.size) {
            favorites - newFavorites.toSet()
        } else {
            return
        }
        favorites = newFavorites

        for (i in 0 until itemCount) {
            val item = getItem(i) ?: continue

            if (item == favorite.first()) {
                notifyItemChanged(i)
                break
            }
        }
    }

    inner class SearchItemViewHolder(
        private val binding: ItemSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchItem) = with(binding) {

            searchitem = item

            favoriteIcon.setOnClickListener {
                clickFavorite?.invoke(item)
            }
            thumbnail.setOnClickListener {
                clickItem?.invoke(item)
            }
            val isFavorite = favorites.contains(item)

            if (isFavorite) {
                favoriteIcon.setImageResource(R.drawable.ic_favorite_on)
            } else {
                favoriteIcon.setImageResource(R.drawable.ic_favorite_off)
            }
        }

    }
}


private val comparator = object : DiffUtil.ItemCallback<SearchItem>() {
    override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem.thumbnail_url == newItem.thumbnail_url
    }

    override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem.thumbnail_url == newItem.thumbnail_url
                && oldItem.datetime == newItem.datetime
    }

}