package com.ooma.archsample.data.model

data class UsersSearchResult(
    val total_count: Long,
    val incomplete_results: Boolean,
    val items: List<User>
)