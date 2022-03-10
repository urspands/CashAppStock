package com.raj.stocksapp.view

import android.os.Bundle
import com.raj.stocksapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var _binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
    }
}