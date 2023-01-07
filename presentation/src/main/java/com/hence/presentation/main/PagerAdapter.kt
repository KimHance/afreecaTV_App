package com.hence.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hence.domain.model.Category
import com.hence.presentation.broadcast.BroadcastFragment

class PagerAdapter(
    fragment: Fragment,
    private val categoryList: List<Category>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = TAB_NUM

    override fun createFragment(position: Int): Fragment {
        return BroadcastFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CATEGORY, categoryList[position])
            }
        }
    }

    companion object {
        const val TAB_NUM = 3
        const val ARG_CATEGORY = "key_category"
    }
}