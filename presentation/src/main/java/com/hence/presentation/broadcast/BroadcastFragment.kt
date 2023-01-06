package com.hence.presentation.broadcast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.hence.domain.model.Broadcast
import com.hence.domain.model.CategoryType
import com.hence.presentation.R
import com.hence.presentation.base.BaseFragment
import com.hence.presentation.broadcast.adapter.BroadcastPagingAdapter
import com.hence.presentation.category.CategoryFragmentDirections
import com.hence.presentation.databinding.FragmentBroadcastBinding
import com.hence.presentation.main.PagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BroadcastFragment :
    BaseFragment<FragmentBroadcastBinding>(R.layout.fragment_broadcast) {

    private val broadcastViewModel: BroadcastViewModel by viewModels()
    private val broadcastAdapter: BroadcastPagingAdapter by lazy {
        BroadcastPagingAdapter(itemClickListener = { broadcast ->
            doOnClick(broadcast)
        })
    }
    private lateinit var categoryNum: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        collectFlow()
    }

    private fun initView() {
        binding.rvBroadcast.adapter = broadcastAdapter
    }

    private fun initData() {
        arguments?.takeIf { it.containsKey(PagerAdapter.ARG_CATEGORY) }?.apply {
            categoryNum = getString(PagerAdapter.ARG_CATEGORY) ?: ""
            broadcastViewModel.getBroadcastList(categoryNum)
        }
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                with(broadcastViewModel) {
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

    private fun doOnClick(broadcast: Broadcast) {
        val action = CategoryFragmentDirections.actionCategoryFragmentToDetailFragment(broadcast)
        requireView().findNavController().navigate(action)
    }

}