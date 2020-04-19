package com.ooma.archsample.domain.usecase

import com.ooma.archsample.data.model.UsersSearchResult
import com.ooma.archsample.data.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

class SearchUsers(
    private val repository: UserRepository,
    private val debounceTimeout: Long = 400
) : SubjectUseCase<UsersSearchResult, SearchUsers.Params>() {

    override fun buildSubjectObservable(): Observable<UsersSearchResult> {
        return subject.debounce(debounceTimeout, TimeUnit.MILLISECONDS)
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