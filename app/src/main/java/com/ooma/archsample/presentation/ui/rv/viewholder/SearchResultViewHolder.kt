package com.ooma.archsample.presentation.ui.rv.viewholder

import android.view.View
import androidx.annotation.StringRes
import com.ooma.archsample.R
import com.ooma.archsample.domain.model.Initial
import com.ooma.archsample.domain.model.SearchResult
import com.ooma.archsample.domain.model.SearchItem
import com.ooma.archsample.presentation.ui.rv.adapter.SearchUserClickListener
import com.ooma.archsample.presentation.ui.utils.PicassoUtils
import kotlinx.android.synthetic.main.item_rv_search_initial.view.*
import kotlinx.android.synthetic.main.item_rv_user_search_result.view.*

sealed class SearchItemViewHolder(itemView: View) : BaseViewHolder<SearchItem>(itemView) {

    class SearchInitialViewHolder(
        itemView: View,
        @StringRes private val hintResId: Int
    ) : SearchItemViewHolder(itemView) {

        override fun bind(model: SearchItem) {
            if (model !is Initial) return

            itemView.apply {
                initial_state_hint_text_view.setText(hintResId)
            }
        }
    }

    class SearchResultViewHolder(
        itemView: View,
        private val clickListener: SearchUserClickListener
    ) : SearchItemViewHolder(itemView) {

        override fun bind(model: SearchItem) {
            if (model !is SearchResult) return

            PicassoUtils.from(itemView)
                .load(model.suggestion.avatarUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_input_black_24dp)
                .into(itemView.avatar_image_view)

            itemView.apply {
                itemView.login_text_view.text = model.suggestion.login
                setOnClickListener { clickListener.onSuggestionClick(model.suggestion) }
            }
        }
    }
}

