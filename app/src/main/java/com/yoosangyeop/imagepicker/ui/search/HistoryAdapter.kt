package com.yoosangyeop.imagepicker.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yoosangyeop.imagepicker.databinding.ItemHistoryBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private var history = listOf<String>()
    var clickRemove : ((String)->Unit)? = null
    var clickItem :  ((String)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(layoutInflater, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        if (position == RecyclerView.NO_POSITION) return

        holder.bind(history[position])
    }

    override fun getItemCount(): Int = history.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateHistory(history: List<String>) {
        this.history = history
        notifyDataSetChanged()
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(query: String) = with(binding) {
            queryText.text = query

            queryText.setOnClickListener {
                clickItem?.invoke(query)
            }

            closeIcon.setOnClickListener {
                clickRemove?.invoke(query)
            }
        }
    }
}
