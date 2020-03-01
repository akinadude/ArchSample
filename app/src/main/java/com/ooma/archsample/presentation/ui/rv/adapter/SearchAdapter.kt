package com.ooma.archsample.presentation.ui.rv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ooma.archsample.R
import com.ooma.archsample.data.model.UserProfile
import com.ooma.archsample.presentation.ui.rv.viewholder.SearchViewHolder

class SearchAdapter : ListAdapter<UserProfile, SearchViewHolder>(diffCallback) {

    //todo Use short User instead UserProfile

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<UserProfile>() {
            override fun areItemsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchViewHolder(inflater.inflate(R.layout.item_rv_search_result, parent, false))
    }

    override fun onBindViewHolder(viewHolder: SearchViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }
}