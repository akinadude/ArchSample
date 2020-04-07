package com.ooma.archsample.domain.usecase

import com.ooma.archsample.data.model.UsersSearchResult
import com.ooma.archsample.data.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchUsers(private val repository: UserRepository) :
    BaseUseCase<UsersSearchResult, SearchUsers.Params>() {

    private val subject: PublishSubject<String> = PublishSubject.create()

    override fun run(params: Params): Single<out UsersSearchResult> =
        repository.searchUsers(params.searchText)

    fun observeSubject(
        onSuccess: (UsersSearchResult) -> Unit = {},
        onFailure: (Throwable) -> Unit = {}
    ): Disposable {
        return subject.debounce(300, TimeUnit.MILLISECONDS)
            .filter { it.isNotBlank() }
            .distinctUntilChanged()
            .switchMapSingle { searchText ->
                repository.searchUsers(searchText)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
            .subscribe(onSuccess, onFailure)
    }

    fun setSubjectItem(searchText: String) {
        subject.onNext(searchText)
    }

    data class Params(val searchText: String)
}