package com.ooma.archsample.data.source.persistence

import android.content.Context
import androidx.core.content.ContextCompat

//todo Need to proceed with this experiment
//todo A very similar thought about startActivity was in Kotlin in action book
inline fun <reified T> Context.systemService() =
    ContextCompat.getSystemService(this, T::class.java)