package com.hence.presentation.broadcast

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hence.domain.model.Broadcast
import com.hence.domain.model.Category
import com.hence.domain.model.CategoryType
import com.hence.presentation.R
import com.hence.presentation.base.BaseFragment
import com.hence.presentation.broadcast.adapter.BroadcastPagingAdapter
import com.hence.presentation.category.CategoryFragmentDirections
import com.hence.presentation.databinding.FragmentBroadcastBinding
import com.hence.presentation.main.PagerAdapter.Companion.ARG_CATEGORY
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
    private lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        swipeToRefresh()
        collectFlow()
        checkRecyclerviewIsTopState()
    }

    private fun initView() {
        binding.apply {
            rvBroadcast.adapter = broadcastAdapter
            btnUpScroll.setOnClickListener {
                rvBroadcast.smoothScrollToPosition(0)
            }
            rvDetailCategory.isGone = category.child.isEmpty()
        }
    }

    private fun swipeToRefresh() {
        binding.srlBroadcast.setOnRefreshListener {
            broadcastViewModel.refreshBroadcastList(category.number)
        }
    }

    private fun checkRecyclerviewIsTopState() {
        with(binding) {
            rvBroadcast.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    btnUpScroll.isVisible = recyclerView.computeVerticalScrollOffset() != 0
                }
            })
        }
    }

    private fun initData() {
        arguments?.takeIf { it.containsKey(ARG_CATEGORY) }?.apply {
            category = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getSerializable(ARG_CATEGORY, Category::class.java)
            } else {
                getSerializable(ARG_CATEGORY) as Category
            } ?: Category()
            broadcastViewModel.getBroadcastList(category.number)
        }
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                with(broadcastViewModel) {
                    when (CategoryType.getCategory(category.number)) {
                        CategoryType.TALK -> {
                            talkBroadcastList.collectLatest { data ->
                                broadcastAdapter.submitData(data)
                                binding.srlBroadcast.isRefreshing = false
                            }
                        }
                        CategoryType.GAME -> {
                            gameBroadcastList.collectLatest { data ->
                                broadcastAdapter.submitData(data)
                                binding.srlBroadcast.isRefreshing = false
                            }
                        }
                        CategoryType.EAT -> {
                            eatBroadcastList.collectLatest { data ->
                                broadcastAdapter.submitData(data)
                                binding.srlBroadcast.isRefreshing = false
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