package com.hence.presentation.category.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.hence.domain.model.Broadcast
import com.hence.presentation.databinding.ItemBroadcastBinding

class BroadcastViewHolder(
    private val binding: ItemBroadcastBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Broadcast) {
        binding.apply {
            broadcast = item
            executePendingBindings()
        }
    }
}