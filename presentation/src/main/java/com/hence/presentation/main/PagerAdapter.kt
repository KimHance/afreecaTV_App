package com.hence.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hence.domain.model.Category
import com.hence.presentation.category.eat.EatFragment
import com.hence.presentation.category.game.GameFragment
import com.hence.presentation.category.talk.TalkFragment

class PagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val categoryList: List<Category>
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = TAB_NUM

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                val fragment = TalkFragment()
                fragment.arguments = Bundle().apply {
                    putString(ARG_CATEGORY, categoryList[position].number)
                }
                return fragment
            }
            1 -> {
                val fragment = EatFragment()
                fragment.arguments = Bundle().apply {
                    putString(ARG_CATEGORY, categoryList[position].number)
                }
                return fragment
            }

            else -> {
                val fragment = GameFragment()
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