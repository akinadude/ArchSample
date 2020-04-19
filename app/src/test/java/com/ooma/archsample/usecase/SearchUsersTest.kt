package com.ooma.archsample.usecase

import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.model.UsersSearchResult
import com.ooma.archsample.data.repository.UserRepository
import com.ooma.archsample.domain.usecase.SearchUsers
import com.ooma.archsample.support.extensions.assertNoErrorsHasValues
import com.ooma.archsample.support.extensions.assertNoErrorsNoValues
import com.ooma.archsample.support.rx.RxStubsCreator.wrapError
import com.ooma.archsample.support.rx.RxStubsCreator.wrapSingle
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class SearchUsersTest : ReactiveUseCaseTest() {

    @Mock
    private lateinit var repository: UserRepository

    private lateinit var searchUsers: SearchUsers

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchUsers = SearchUsers(repository)
    }

    @Test
    fun `Search for users when entering empty search key`() {
        val testObserver = searchUsers.buildSubjectObservable().test()
        searchUsers.setSearchText("")

        testObserver.assertNoErrors()
        testObserver.assertValueCount(0)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `Search for users when simulating search keys typing`() {
        val searchResultFirst = UsersSearchResult(1, false, listOf(User(1)))
        val searchResultSecond = UsersSearchResult(2, false, listOf(User(1), User(2)))
        `when`(repository.searchUsers("abc")).thenReturn(wrapSingle(searchResultFirst))
        `when`(repository.searchUsers("abcd")).thenReturn(wrapSingle(searchResultSecond))

        val testObserver = searchUsers.buildSubjectObservable().test()

        searchUsers.setSearchText("a")
        testSchedulerRule.testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)
        testObserver.assertNoErrorsNoValues()

        searchUsers.setSearchText("ab")
        testSchedulerRule.testScheduler.advanceTimeBy(200, TimeUnit.MILLISECONDS)
        testObserver.assertNoErrorsNoValues()

        searchUsers.setSearchText("abc")
        testSchedulerRule.testScheduler.advanceTimeBy(300, TimeUnit.MILLISECONDS)
        testObserver.assertNoErrorsNoValues()

        testSchedulerRule.testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)
        testObserver.assertNoErrorsHasValues(searchResultFirst)

        searchUsers.setSearchText("abcd")
        testSchedulerRule.testScheduler.advanceTimeBy(400, TimeUnit.MILLISECONDS)
        testObserver.assertNoErrorsHasValues(searchResultFirst, searchResultSecond)

        verify(repository).searchUsers("abc")
        verify(repository).searchUsers("abcd")
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `Search for users when a failure is occurred in repository`() {
        val searchKey = "searchKey"
        `when`(repository.searchUsers(searchKey)).thenReturn(wrapError(errorMessage))

        val testObserver = searchUsers.buildSubjectObservable().test()
        searchUsers.setSearchText(searchKey)
        testSchedulerRule.testScheduler.advanceTimeBy(400, TimeUnit.MILLISECONDS)

        testObserver.assertNotComplete()
            .assertFailureAndMessage(Exception::class.java, errorMessage)
        verify(repository).searchUsers(searchKey)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `Search for users when entering the same key twice`() {
        val searchKeyFirst = "a"
        val searchKeySecond = "ab"
        val searchResultFirst = UsersSearchResult(1, false, listOf(User(1)))
        val searchResultSecond = UsersSearchResult(2, false, listOf(User(1), User(2)))
        `when`(repository.searchUsers(searchKeyFirst)).thenReturn(wrapSingle(searchResultFirst))
        `when`(repository.searchUsers(searchKeySecond)).thenReturn(wrapSingle(searchResultSecond))

        val testObserver = searchUsers.buildSubjectObservable().test()
        searchUsers.setSearchText(searchKeyFirst)
        testSchedulerRule.testScheduler.advanceTimeBy(400, TimeUnit.MILLISECONDS)
        searchUsers.setSearchText(searchKeyFirst)
        testSchedulerRule.testScheduler.advanceTimeBy(400, TimeUnit.MILLISECONDS)
        searchUsers.setSearchText(searchKeySecond)
        testSchedulerRule.testScheduler.advanceTimeBy(400, TimeUnit.MILLISECONDS)

        testObserver.assertNoErrors()
        testObserver.assertValues(searchResultFirst, searchResultSecond)
        verify(repository).searchUsers(searchKeyFirst)
        verify(repository).searchUsers(searchKeySecond)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `Search for users when starting a search requests when the previous one is still performing`() {
        val searchKeyFirst = "a"
        val searchKeySecond = "ab"
        val searchResultFirst = UsersSearchResult(1, false, listOf(User(1)))
        val searchResultSecond = UsersSearchResult(2, false, listOf(User(1), User(2)))
        `when`(repository.searchUsers(searchKeyFirst)).thenReturn(
            wrapSingle(searchResultFirst).delay(1, TimeUnit.SECONDS)
        )
        `when`(repository.searchUsers(searchKeySecond)).thenReturn(wrapSingle(searchResultSecond))

        val testObserver = searchUsers.buildSubjectObservable().test()
        searchUsers.setSearchText(searchKeyFirst)
        testSchedulerRule.testScheduler.advanceTimeBy(400, TimeUnit.MILLISECONDS)
        searchUsers.setSearchText(searchKeySecond)
        testSchedulerRule.testScheduler.advanceTimeBy(400, TimeUnit.MILLISECONDS)

        testObserver.assertNoErrorsHasValues(searchResultSecond)
        verify(repository).searchUsers(searchKeyFirst)
        verify(repository).searchUsers(searchKeySecond)
        verifyNoMoreInteractions(repository)
    }
}