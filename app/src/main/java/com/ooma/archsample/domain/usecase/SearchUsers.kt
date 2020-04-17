package com.ooma.archsample.domain.usecase

import com.ooma.archsample.data.model.UsersSearchResult
import com.ooma.archsample.data.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchUsers(private val repository: UserRepository) :
    SubjectUseCase<UsersSearchResult, SearchUsers.Params>() {

    override fun buildSubjectObservable(): Observable<out UsersSearchResult> {
        return subject.debounce(400, TimeUnit.MILLISECONDS)
            .filter { it.searchText.isNotBlank() }
            .distinctUntilChanged()
            .switchMapSingle {
                repository.searchUsers(it.searchText)
                    .subscribeOn(Schedulers.io())
            }.observeOn(AndroidSchedulers.mainThread())
    }

    fun setSearchText(searchText: String) {
        super.setSubjectItem(Params(searchText))
    }

    data class Params(val searchText: String)
}