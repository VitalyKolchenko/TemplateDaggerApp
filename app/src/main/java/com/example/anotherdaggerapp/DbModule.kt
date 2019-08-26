package com.example.anotherdaggerapp

import dagger.Module
import dagger.Provides
import io.realm.Realm

/**
 * Created by vitaly on 2019-08-17.
 */

@Module
class DbModule {
    @PerActivity
    @Provides
    fun realm() = Realm.getDefaultInstance()
}