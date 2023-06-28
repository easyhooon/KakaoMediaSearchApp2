package com.kenshi.presentation.view.ui.screens.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.navigation.fragment.navArgs
import com.kenshi.presentation.R
import com.kenshi.presentation.databinding.FragmentSearchDetailBinding
import com.kenshi.presentation.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchDetailFragment :
    BaseFragment<FragmentSearchDetailBinding>(R.layout.fragment_search_detail) {


    private val args by navArgs<SearchDetailFragmentArgs>()
    override fun getViewBinding() = FragmentSearchDetailBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initView() {
        val url = args.url
        binding.wvSearchDetail.apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            // 자바 스크립트 허용
            settings.javaScriptEnabled = true
            // 로컬 스토리지 허용
            settings.domStorageEnabled = true
            loadUrl(url)
        }
    }

    inner class WebViewClient : android.webkit.WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)

            binding.clpbSearchDetail.show()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            binding.clpbSearchDetail.hide()
        }
    }

    inner class WebChromeClient : android.webkit.WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            binding.clpbSearchDetail.progress = newProgress
        }
    }

    // WebView 를 Fragment 에서 사용시 destroy 처리 필요
    override fun onDestroyView() {
        binding.wvSearchDetail.destroy()
        super.onDestroyView()
    }
}