package com.hence.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hence.presentation.databinding.ActivityMainBinding
import com.hence.presentation.utils.NetworkManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }
}