package com.ooma.archsample.usecase

import com.ooma.archsample.support.rules.TestSchedulerRule
import org.junit.Rule

abstract class ReactiveUseCaseTest {

    protected val testSchedulerRule = TestSchedulerRule()

    @Rule
    fun getTestScheduler() = testSchedulerRule

    protected val errorMessage = "Exception"
}