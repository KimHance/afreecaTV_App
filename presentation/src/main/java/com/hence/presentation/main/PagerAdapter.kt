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
        val fragment = BroadcastFragment()
        when (position) {
            0 -> {
                fragment.arguments = Bundle().apply {
                    putString(ARG_CATEGORY, categoryList[position].number)
                }
                return fragment
            }
            1 -> {
                fragment.arguments = Bundle().apply {
                    putString(ARG_CATEGORY, categoryList[position].number)
                }
                return fragment
            }
            else -> {
                fragment.arguments = Bundle().apply {
                    putString(ARG_CATEGORY, categoryList[position].number)
                }
                return fragment
            }
        }
    }

    companion object {
        const val TAB_NUM = 3
        const val ARG_CATEGORY = "key_category"
    }
}