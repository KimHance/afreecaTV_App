package com.hence.presentation.broadcast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.hence.domain.model.Broadcast
import com.hence.presentation.R
import com.hence.presentation.broadcast.viewholder.BroadcastViewHolder

class BroadcastPagingAdapter :
    PagingDataAdapter<Broadcast, BroadcastViewHolder>(broadcastDiffUtil) {

    override fun onBindViewHolder(holder: BroadcastViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BroadcastViewHolder {
        return BroadcastViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_broadcast,
                parent,
                false
            )
        )
    }

    companion object {
        private val broadcastDiffUtil = object : DiffUtil.ItemCallback<Broadcast>() {
            override fun areItemsTheSame(oldItem: Broadcast, newItem: Broadcast): Boolean =
                oldItem.broadNum == newItem.broadNum

            override fun areContentsTheSame(oldItem: Broadcast, newItem: Broadcast): Boolean =
                oldItem == newItem
        }
    }
}