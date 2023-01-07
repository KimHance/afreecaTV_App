package com.hence.presentation.broadcast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hence.domain.model.DetailCategory
import com.hence.presentation.R
import com.hence.presentation.broadcast.viewholder.CategoryDetailViewHolder

class CategoryDetailAdapter(
    private val itemClickListener: (DetailCategory) -> Unit
) : ListAdapter<DetailCategory, CategoryDetailViewHolder>(categoryDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryDetailViewHolder {
        return CategoryDetailViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_category_detail,
                parent,
                false
            ),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: CategoryDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val categoryDiffUtil = object : DiffUtil.ItemCallback<DetailCategory>() {
            override fun areItemsTheSame(oldItem: DetailCategory, newItem: DetailCategory) =
                oldItem.number == newItem.number

            override fun areContentsTheSame(oldItem: DetailCategory, newItem: DetailCategory) =
                oldItem == newItem
        }
    }
}