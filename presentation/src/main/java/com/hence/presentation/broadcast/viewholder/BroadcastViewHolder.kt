package com.hence.presentation.broadcast.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.hence.domain.model.Broadcast
import com.hence.presentation.databinding.ItemBroadcastBinding

class BroadcastViewHolder(
    private val binding: ItemBroadcastBinding,
    private val itemClickListener: (Broadcast) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Broadcast) {
        binding.apply {
            broadcast = item
            itemView.setOnClickListener {
                itemClickListener(item)
            }
            executePendingBindings()
        }
    }
}