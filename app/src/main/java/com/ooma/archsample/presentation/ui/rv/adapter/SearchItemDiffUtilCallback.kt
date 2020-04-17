package com.ooma.archsample.presentation.ui.rv.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ooma.archsample.domain.model.SearchItem
import com.ooma.archsample.domain.model.SearchResult

//Used for simple RecyclerView.Adapter for diff calculation
class SearchItemDiffUtilCallback(
    private val oldList: List<SearchItem>,
    private val newList: List<SearchItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return if (oldItem is SearchResult && newItem is SearchResult) {
            oldItem.suggestion.id == newItem.suggestion.id
        } else {
            oldItem == newItem
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}