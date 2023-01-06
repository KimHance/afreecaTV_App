package com.hence.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.hence.presentation.R
import com.hence.presentation.base.BaseFragment
import com.hence.presentation.databinding.FragmentDetailBinding

class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val navArgs by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
    }

    private fun initData() {
        binding.broadcast = navArgs.broadcast
    }

    private fun initView() {
        binding.ivBack.setOnClickListener {
            requireView().findNavController().popBackStack()
        }
    }

}
