package com.kenshi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.kenshi.presentation.adapter.viewholder.SearchLoadStateViewHolder
import com.kenshi.presentation.databinding.ItemLoadStateBinding

class SearchLoadStateAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<SearchLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): SearchLoadStateViewHolder {
        return SearchLoadStateViewHolder(
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            retry
        )
    }

    override fun onBindViewHolder(holder: SearchLoadStateViewHolder, loadState: LoadState) {
        // binding 시 loadState 를 전달
        holder.bind(loadState)
    }
}
