package com.hence.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.hence.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mainViewModel.getCategoryList()
        initView()
    }

    private fun initView() {
        lifecycleScope.launch {
            mainViewModel.categoryList.collect { categoryList ->
                binding.apply {
                    vpMain.adapter = PagerAdapter(supportFragmentManager, lifecycle)
                    if (categoryList.isNotEmpty()) {
                        TabLayoutMediator(tlMain, vpMain) { tab, position ->
                            tab.text = categoryList[position].name
                        }.attach()
                    }
                }
            }
        }
    }
}