package com.hence.presentation.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hence.domain.model.CategoryType
import com.hence.presentation.R
import com.hence.presentation.base.BaseFragment
import com.hence.presentation.category.adapter.BroadcastPagingAdapter
import com.hence.presentation.databinding.FragmentCategoryBinding
import com.hence.presentation.main.PagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {

    private val categoryViewModel: CategoryViewModel by viewModels()
    private val broadcastAdapter: BroadcastPagingAdapter by lazy {
        BroadcastPagingAdapter()
    }
    private lateinit var categoryNum: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
        collectFlow()
    }

    private fun initView() {
        binding.rvBroadcast.adapter = broadcastAdapter
    }

    private fun initData() {
        arguments?.takeIf { it.containsKey(PagerAdapter.ARG_CATEGORY) }?.apply {
            categoryNum = getString(PagerAdapter.ARG_CATEGORY) ?: ""
            categoryViewModel.getBroadcastList(categoryNum)
        }
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                with(categoryViewModel) {
                    when (CategoryType.getCategory(categoryNum)) {
                        CategoryType.TALK -> {
                            talkBroadcastList.collectLatest { data ->
                                broadcastAdapter.submitData(data)
                            }
                        }
                        CategoryType.GAME -> {
                            gameBroadcastList.collectLatest { data ->
                                broadcastAdapter.submitData(data)
                            }
                        }
                        CategoryType.EAT -> {
                            eatBroadcastList.collectLatest { data ->
                                broadcastAdapter.submitData(data)
                            }
                        }
                        CategoryType.UNDEFINED -> {
                            // TODO error
                        }
                    }
                }
            }
        }
    }

}