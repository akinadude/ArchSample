package com.ooma.archsample.domain.model

sealed class SearchItem

data class SearchResult(val suggestion: SearchUserSuggestion) : SearchItem()

object Initial : SearchItem()

object NothingFound : SearchItem()