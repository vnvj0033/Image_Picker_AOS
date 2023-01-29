package com.yoosangyeop.imagepicker.feature.search.ui.gallery

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.yoosangyeop.imagepicker.feature.search.R
import com.yoosangyeop.imagepicker.feature.search.databinding.ItemSearchBinding
import com.yoosangyeop.imagepicker.model.search.SearchItem

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favorites: List<SearchItem> = listOf()
    var clickRemove: ((SearchItem) -> Unit)? = null
    var clickItem: ((SearchItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(layoutInflater, parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        if (position == RecyclerView.NO_POSITION)
            return

        holder.bind(favorites[position])
    }

    override fun getItemCount(): Int = favorites.size

    override fun getItemId(position: Int): Long = favorites[position].hashCode().toLong()


    @SuppressLint("NotifyDataSetChanged")
    fun updateFavorites(newFavorites: List<SearchItem>) {
        favorites = newFavorites
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(favorites: SearchItem) = with(binding){

            searchitem = favorites

            dateTime.isGone = true
            favoriteIcon.setImageResource(R.drawable.ic_favorite_on)

            favoriteIcon.setOnClickListener {
                clickRemove?.invoke(favorites)
            }

            thumbnail.setOnClickListener {
                clickItem?.invoke(favorites)
            }
        }

    }
}
