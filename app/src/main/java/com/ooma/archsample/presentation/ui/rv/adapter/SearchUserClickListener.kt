package com.ooma.archsample.presentation.ui.rv.adapter

import com.ooma.archsample.domain.model.SearchUserSuggestion

interface SearchUserClickListener {
    fun onSuggestionClick(suggestion: SearchUserSuggestion)
}