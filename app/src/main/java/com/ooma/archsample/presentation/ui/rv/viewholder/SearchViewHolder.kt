package com.ooma.archsample.presentation.ui.rv.viewholder

import android.view.View
import com.ooma.archsample.R
import com.ooma.archsample.domain.model.SearchUserSuggestion
import com.ooma.archsample.presentation.ui.utils.PicassoUtils
import com.ooma.archsample.presentation.ui.rv.adapter.SearchUserClickListener
import kotlinx.android.synthetic.main.rv_item_user_search_result.view.*

class SearchViewHolder(
        itemView: View,
        private val clickListener: SearchUserClickListener
) : BaseViewHolder<SearchUserSuggestion>(itemView) {

    override fun bind(model: SearchUserSuggestion) {
        PicassoUtils.from(itemView)
                .load(model.avatarUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_input_black_24dp)
                .into(itemView.avatar_image_view)

        itemView.apply {
            itemView.login_text_view.text = model.login
            setOnClickListener { clickListener.onSuggestionClick(model) }
        }
    }
}