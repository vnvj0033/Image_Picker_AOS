package com.yoosangyeop.imagepicker.ui.gallery

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yoosangyeop.imagepicker.R
import com.yoosangyeop.imagepicker.databinding.ItemSearchBinding

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favoriteUrls: List<String> = listOf()
    var clickRemove: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(layoutInflater, parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        if (position == RecyclerView.NO_POSITION)
            return

        holder.bind(favoriteUrls[position])
    }

    override fun getItemCount(): Int = favoriteUrls.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavorites(newFavorites: List<String>) {
        favoriteUrls = newFavorites
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteUrl: String) = with(binding){
            typeIcon.isGone = true
            dateTime.isGone = true

            Glide.with(root.context)
                .load(favoriteUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(thumbnail)

            favoriteIcon.setImageResource(R.drawable.ic_favorite_on)

            favoriteIcon.setOnClickListener {
                clickRemove?.invoke(favoriteUrl)
            }
        }

    }
}