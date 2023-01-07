package com.hence.presentation.broadcast.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.hence.domain.model.Broadcast
import com.hence.presentation.databinding.ItemBroadcastBinding

class BroadcastViewHolder(
    private val binding: ItemBroadcastBinding,
    private val itemClickListener: (Broadcast) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        itemView.setOnClickListener {
            binding.broadcast?.run {
                itemClickListener(this)
            }
        }
    }

    fun bind(item: Broadcast) {
        binding.apply {
            broadcast = item
            executePendingBindings()
        }
    }
}