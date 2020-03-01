package com.ooma.archsample.presentation.ui.rv.viewholder

import android.view.View
import com.ooma.archsample.R
import com.ooma.archsample.data.model.UserProfile
import com.ooma.archsample.presentation.ui.PicassoUtils
import kotlinx.android.synthetic.main.item_rv_search_result.view.*

class SearchViewHolder(itemView: View) : BaseViewHolder<UserProfile>(itemView) {

    override fun bind(model: UserProfile) {
        itemView.login_text_view.text = model.login
        PicassoUtils.from(itemView)
            .load(model.avatar_url)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_input_black_24dp)
            .into(itemView.avatar_image_view)
    }
}