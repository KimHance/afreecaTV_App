package com.hence.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.hence.presentation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.apply {
            vpMain.adapter = PagerAdapter(supportFragmentManager, lifecycle)
            TabLayoutMediator(tlMain, vpMain) { tab, position ->
                tab.text = "${position + 1}"
            }.attach()
        }
    }
}