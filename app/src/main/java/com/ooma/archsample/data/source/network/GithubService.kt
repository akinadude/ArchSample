package com.ooma.archsample.data.source.network

import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.model.UsersSearchResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("/users/{username}")
    fun getUserProfile(@Path("username") username: String): Single<User>

    @GET("/search/users")
    fun searchUsers(@Query("q") searchText: String): Single<UsersSearchResult>
}