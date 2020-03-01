package com.ooma.archsample.presentation.ui.rv.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView) {
    open fun onAttached() = Unit

    open fun onDetached() = Unit

    abstract fun bind(model: T)
}