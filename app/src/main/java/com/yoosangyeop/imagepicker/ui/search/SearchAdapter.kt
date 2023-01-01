package com.yoosangyeop.imagepicker.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoosangyeop.imagepicker.R
import com.yoosangyeop.imagepicker.data.model.SearchItem
import com.yoosangyeop.imagepicker.databinding.ItemSearchBinding

class SearchAdapter : PagingDataAdapter<SearchItem, SearchAdapter.SearchItemViewHolder>(comparator) {
    var click: ((SearchItem) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(layoutInflater, parent, false)
        return SearchItemViewHolder(click, binding)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        val comparator = object : DiffUtil.ItemCallback<SearchItem>() {
            override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem.thumbnail_url == newItem.thumbnail_url
            }

            override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem.thumbnail_url == newItem.thumbnail_url
            }

        }
    }

    class SearchItemViewHolder(
        private val click: ((SearchItem) -> Unit)?,
        private val binding: ItemSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchItem?) = with(binding) {
            item ?: return@with

            Glide.with(root.context)
                .load(item.thumbnail_url)
                .into(thumbnail)

            val isFavorite = false
            // TODO: isFavorite 구현

            if (isFavorite) {
                favoriteIcon.setImageResource(R.drawable.ic_favorite_on)
            } else {
                favoriteIcon.setImageResource(R.drawable.ic_favorite_off)
            }

            click?.invoke(item)
        }

    }
}