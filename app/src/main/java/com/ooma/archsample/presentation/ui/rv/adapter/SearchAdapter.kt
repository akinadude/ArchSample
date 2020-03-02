package com.ooma.archsample.presentation.ui.rv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ooma.archsample.R
import com.ooma.archsample.domain.model.SearchUserSuggestion
import com.ooma.archsample.presentation.ui.rv.viewholder.SearchViewHolder

class SearchAdapter(
        private val clickListener: SearchUserClickListener
) : ListAdapter<SearchUserSuggestion, SearchViewHolder>(diffCallback) {

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<SearchUserSuggestion>() {
            override fun areItemsTheSame(oldItem: SearchUserSuggestion, newItem: SearchUserSuggestion): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SearchUserSuggestion, newItem: SearchUserSuggestion): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchViewHolder(
                inflater.inflate(R.layout.rv_item_user_search_result, parent, false),
                clickListener
        )
    }

    override fun onBindViewHolder(viewHolder: SearchViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }
}