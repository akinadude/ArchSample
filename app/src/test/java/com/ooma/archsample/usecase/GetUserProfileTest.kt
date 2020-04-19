package com.ooma.archsample.usecase

import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.repository.UserRepository
import com.ooma.archsample.domain.usecase.GetUserProfile
import com.ooma.archsample.support.rx.RxStubsCreator.wrapError
import com.ooma.archsample.support.rx.RxStubsCreator.wrapSingle
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class GetUserProfileTest : ReactiveUseCaseTest() {

    private lateinit var getUserProfile: GetUserProfile

    @Mock
    private lateinit var repository: UserRepository

    private val username = "username"
    private val user = User(1)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getUserProfile = GetUserProfile(repository)
    }

    @Test
    fun `Get user profile when data is obtained successfully from repository`() {
        `when`(repository.getUserProfile(username)).thenReturn(wrapSingle(user))

        val testObserver = getUserProfile.buildUseCaseSingle(GetUserProfile.Params(username)).test()
        testSchedulerRule.testScheduler.triggerActions()

        testObserver.assertComplete()
            .assertNoErrors()
            .assertValue(user)
        verify(repository).getUserProfile(username)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `Get user profile when a failure is occurred in repository`() {
        `when`(repository.getUserProfile(username)).thenReturn(wrapError(errorMessage))

        val testObserver = getUserProfile.buildUseCaseSingle(GetUserProfile.Params(username)).test()
        testSchedulerRule.testScheduler.triggerActions()

        testObserver.assertNotComplete()
            .assertFailureAndMessage(Exception::class.java, errorMessage)
        verify(repository).getUserProfile(username)
        verifyNoMoreInteractions(repository)
    }
}