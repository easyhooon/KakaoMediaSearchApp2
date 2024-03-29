package com.kenshi.presentation.view.adapter.viewholder

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.kenshi.presentation.R
import com.kenshi.presentation.databinding.ItemLoadStateBinding

// loadingState 를 관리 해주는 adapter 의 뷰홀더
class SearchLoadStateViewHolder(private val binding: ItemLoadStateBinding, retry: () -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener {
            retry.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        binding.apply {
            if (loadState is LoadState.Error) {
                tvError.text = itemView.context.getString(R.string.error_message)
            }
            pbLoadState.isVisible = loadState is LoadState.Loading
            btnRetry.isVisible = loadState is LoadState.Error
            tvError.isVisible = loadState is LoadState.Error
        }
    }
}
