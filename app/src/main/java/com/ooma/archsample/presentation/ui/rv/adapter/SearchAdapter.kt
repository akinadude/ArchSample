package com.ooma.archsample.presentation.ui.rv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ooma.archsample.R
import com.ooma.archsample.domain.model.Initial
import com.ooma.archsample.domain.model.NothingFound
import com.ooma.archsample.domain.model.SearchItem
import com.ooma.archsample.domain.model.SearchResult
import com.ooma.archsample.presentation.errors.UnknownItemTypeException
import com.ooma.archsample.presentation.ui.rv.viewholder.SearchItemViewHolder

class SearchAdapter(
    private val clickListener: SearchUserClickListener,
    @StringRes var hintTextResId: Int = R.string.search_initial_hint
) : ListAdapter<SearchItem, SearchItemViewHolder>(diffCallback) {

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<SearchItem>() {
            override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean =
                if (oldItem is SearchResult && newItem is SearchResult) {
                    oldItem.suggestion.id == newItem.suggestion.id
                } else {
                    oldItem == newItem
                }

            override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    /*override fun submitList(list: List<SearchItem>?) {
        super.submitList(list?.let { ArrayList(it) })
    }*/

    /*init {
        setHasStableIds(true)
    }*/

    enum class ItemType(val type: Int) {
        SEARCH_RESULT(1),
        NOTHING_FOUND(2),
        PROGRESS(3),
        INITIAL(4)
    }

    /*override fun getItemId(position: Int): Long {
        val item = getItem(position)
        return if (item is SearchResult) {
            item.suggestion.id.toLong()
        } else {
            super.getItemId(position)
        }
    }*/

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is SearchResult -> ItemType.SEARCH_RESULT.type
        is NothingFound -> ItemType.NOTHING_FOUND.type
        is Initial -> ItemType.INITIAL.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ItemType.SEARCH_RESULT.type ->
                SearchItemViewHolder.SearchResultViewHolder(
                    inflater.inflate(R.layout.item_rv_user_search_result, parent, false),
                    clickListener
                )
            /*ItemType.PROGRESS.type ->
                SearchItemViewHolder.SearchResultViewHolder(
                    inflater.inflate(R.layout.item_rv_search_progress, parent, false),
                    clickListeners
                )*/
            /*ItemType.NOTHING_FOUND.type ->
                SearchItemViewHolder.SearchResultViewHolder(
                    inflater.inflate(R.layout.item_rv_search_empty, parent, false),
                    clickListener
                )*/
            ItemType.INITIAL.type ->
                SearchItemViewHolder.SearchInitialViewHolder(
                    inflater.inflate(R.layout.item_rv_search_initial, parent, false),
                    hintTextResId
                )
            else ->
                throw UnknownItemTypeException("Unknown item type $viewType for SearchAdapter")
        }
    }

    override fun onBindViewHolder(viewHolder: SearchItemViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }
}