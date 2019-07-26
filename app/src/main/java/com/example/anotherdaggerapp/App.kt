package com.example.anotherdaggerapp

import androidx.multidex.MultiDexApplication

/**
 * Created by vitaly on 2019-07-22.
 */
class App : MultiDexApplication() {

    var appComponent = DaggerAppComponent.create()

    override fun onCreate() {
        super.onCreate()
    }

}