package com.ooma.archsample.support.rules

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class TestSchedulerRule : TestRule {

    val testScheduler: TestScheduler = TestScheduler()

    override fun apply(s: Statement, d: Description): Statement = object : Statement() {
        override fun evaluate() {
            RxJavaPlugins.setIoSchedulerHandler { testScheduler }
            RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
            RxJavaPlugins.setNewThreadSchedulerHandler { testScheduler }
            RxAndroidPlugins.setMainThreadSchedulerHandler { testScheduler }

            try {
                s.evaluate()
            } finally {
                RxJavaPlugins.reset()
                RxAndroidPlugins.reset()
            }
        }
    }
}