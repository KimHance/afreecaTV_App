package com.hence.presentation.category

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayoutMediator
import com.hence.presentation.R
import com.hence.presentation.base.BaseFragment
import com.hence.presentation.databinding.FragmentCategoryBinding
import com.hence.presentation.main.PagerAdapter
import com.hence.presentation.utils.NetworkManager
import com.hence.presentation.utils.showErrorMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {

    private val categoryViewModel: CategoryViewModel by viewModels()
    private var isNetworkConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNetworkState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isNetworkConnected) {
            initCategory()
        }
        initButtonListener()
    }

    private fun checkNetworkState() {
        if (!NetworkManager.checkNetworkState(requireContext())) {
            requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
            showNetworkErrorDialog()
            isNetworkConnected = false
        } else {
            categoryViewModel.getCategoryList()
            isNetworkConnected = true
        }
    }

    private fun showNetworkErrorDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.error_network))
            .setMessage(getString(R.string.error_network_message))
            .setPositiveButton(
                getString(R.string.check)
            ) { _, _ -> requireActivity().finishAndRemoveTask() }
            .setCancelable(false)
            .create()
            .show()
    }

    private fun initCategory() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoryViewModel.categoryList.collect { categoryList ->
                    when (categoryList) {
                        is CategoryUiState.Success -> {
                            with(binding) {
                                ivRestartFetchCategory.isVisible = false
                                vpMain.adapter =
                                    PagerAdapter(this@CategoryFragment, categoryList.data)
                                TabLayoutMediator(tlMain, vpMain) { tab, position ->
                                    tab.text = categoryList.data[position].name
                                }.attach()
                            }
                        }
                        is CategoryUiState.Error -> {
                            requireContext().showErrorMessage(
                                requireView(),
                                getString(R.string.error_fetch_category)
                            )
                            binding.ivRestartFetchCategory.isVisible = true
                        }
                        is CategoryUiState.Loading -> {}
                    }
                }
            }
        }
    }

    private fun initButtonListener() {
        binding.ivRestartFetchCategory.setOnClickListener {
            categoryViewModel.getCategoryList()
        }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {}
    }
}