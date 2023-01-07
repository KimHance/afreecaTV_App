package com.hence.presentation.broadcast.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.hence.domain.model.DetailCategory
import com.hence.presentation.databinding.ItemCategoryDetailBinding

class CategoryDetailViewHolder(
    private val binding: ItemCategoryDetailBinding,
    private val itemClickListener: (DetailCategory) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: DetailCategory) {
        binding.apply {
            category = item
            itemView.setOnClickListener {
                itemClickListener(item)
            }
            executePendingBindings()
        }
    }
}