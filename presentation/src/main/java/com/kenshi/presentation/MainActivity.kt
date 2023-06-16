package com.kenshi.presentation

import android.content.Intent
import android.os.Bundle
import com.kenshi.presentation.view.SearchViewActivity
import com.kenshi.presentation.databinding.ActivityMainBinding
import com.kenshi.presentation.base.BaseActivity
import com.kenshi.presentation.compose.SearchComposeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.btnSearchView.setOnClickListener {
            startActivity(Intent(this, SearchViewActivity::class.java))
        }
        binding.btnSearchCompose.setOnClickListener {
            startActivity(Intent(this, SearchComposeActivity::class.java))
        }
    }
}