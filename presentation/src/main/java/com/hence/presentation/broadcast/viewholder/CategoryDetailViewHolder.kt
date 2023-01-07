package com.hence.presentation.broadcast.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.hence.domain.model.DetailCategory
import com.hence.presentation.databinding.ItemCategoryDetailBinding

class CategoryDetailViewHolder(
    private val binding: ItemCategoryDetailBinding,
    private val itemClickListener: (DetailCategory) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        itemView.setOnClickListener {
            binding.category?.run {
                itemClickListener(this)
            }
        }
    }

    fun bind(item: DetailCategory) {
        binding.apply {
            category = item
            executePendingBindings()
        }
    }
}