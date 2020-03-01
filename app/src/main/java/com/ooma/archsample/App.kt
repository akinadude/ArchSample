package com.ooma.archsample

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.squareup.picasso.Picasso

class App : Application() {

    lateinit var picasso: Picasso

    override fun onCreate() {
        //MultiDex.install(this);
        super.onCreate()

        initPicasso()
        initLogger()
    }

    private fun initLogger() {
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    private fun initPicasso() {
        picasso = Picasso.Builder(this).build()
    }
}