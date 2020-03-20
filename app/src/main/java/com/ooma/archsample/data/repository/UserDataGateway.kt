package com.ooma.archsample.data.repository

import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.model.UsersSearchResult
import io.reactivex.Single

//Data layer
class UserDataGateway(private val dispatcher: UserDataDispatcher) {

    fun getUserProfile(username: String): Single<User> = dispatcher.getUserProfile(username)

    fun searchUsers(searchText: String): Single<UsersSearchResult> =
        dispatcher.searchUsers(searchText)

    fun saveUserProfile(username: String, user: User) = dispatcher.saveUserProfile(username, user)

    fun saveSearchedUsers(searchText: String, users: List<User>) =
        dispatcher.saveSearchedUsers(searchText, users)
}

//todo Repository is an interface containing getData methods
// There are two implementations: RemoteRepository and LocalRepository
// Both of them take param of type DataDispatcher, which could be local database or api instance

//todo [done] I've got two singles and try to combine them this way
// if the first succeeds then get its result and complete
// if the first fails then get the second and complete

//todo doing...
//todo concat operator. Play with it in a sandbox, read the book, understand how it works
//todo doOnNext, onSuccess operators
//todo andThen operator
//todo firstElement operator
// ConcatMap (optional)

//todo Create error related classes for working with cache. Investigate first

//todo Need to investigate the ideas from articles about independence of framework/libs.
// The article by Antonio Lieva
// Gitfox app

//todo Try to use LruCache structure for memory cache
//todo Use Room as a database solution for disk caching
