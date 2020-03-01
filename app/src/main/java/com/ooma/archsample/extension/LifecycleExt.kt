package com.ooma.archsample.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T)/*T?*/ -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<Throwable>> LifecycleOwner.failure(liveData: L, body: (Throwable)/*T?*/ -> Unit) =
    liveData.observe(this, Observer(body))