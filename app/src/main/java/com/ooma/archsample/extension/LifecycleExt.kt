package com.ooma.archsample.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

//todo T: Model, where Model is a base class for all models of the presentation level
fun <T : Any, L : LiveData<T>> LifecycleOwner.initial(liveData: L, body: (T)/*T?*/ -> Unit) =
    liveData.observe(this, Observer(body))

fun <T : Any, L : LiveData<T>> LifecycleOwner.success(liveData: L, body: (T)/*T?*/ -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<Throwable>> LifecycleOwner.failure(liveData: L, body: (Throwable)/*T?*/ -> Unit) =
    liveData.observe(this, Observer(body))

fun LifecycleOwner.loading(liveData: LiveData<Unit>, body: (Unit) -> Unit) =
    liveData.observe(this, Observer(body))