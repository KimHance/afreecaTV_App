package com.hence.presentation.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayoutMediator
import com.hence.presentation.R
import com.hence.presentation.base.BaseFragment
import com.hence.presentation.databinding.FragmentCategoryBinding
import com.hence.presentation.main.PagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {

    private val categoryViewModel: CategoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategory()
    }

    private fun initCategory() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoryViewModel.categoryList.collect { categoryList ->
                    binding.apply {
                        if (categoryList.isNotEmpty()) {
                            vpMain.adapter =
                                PagerAdapter(this@CategoryFragment, categoryList)
                            TabLayoutMediator(tlMain, vpMain) { tab, position ->
                                tab.text = categoryList[position].name
                            }.attach()
                        }
                    }
                }
            }
        }
    }
}