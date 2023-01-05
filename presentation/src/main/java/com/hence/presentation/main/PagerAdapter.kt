package com.hence.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hence.presentation.category.eat.EatFragment
import com.hence.presentation.category.game.GameFragment
import com.hence.presentation.category.talk.TalkFragment

class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = TAB_NUM

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TalkFragment()
            1 -> EatFragment()
            else -> GameFragment()
        }
    }

    companion object {
        const val TAB_NUM = 3
    }
}