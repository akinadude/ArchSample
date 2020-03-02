package com.ooma.archsample.presentation.ui.utils

import android.content.Context
import android.view.View
import com.ooma.archsample.App
import com.squareup.picasso.Picasso

object PicassoUtils {

    fun from(context: Context): Picasso {
        return (context.applicationContext as App).picasso
    }

    fun from(view: View): Picasso {
        return from(view.context)
    }
}