package com.example.anotherdaggerapp.mvpcontent.model

import com.example.anotherdaggerapp.data.ContentItem
import io.reactivex.Observable
import io.realm.RealmResults

/**
 * Created by vitaly on 2019-08-17.
 */
interface IContentRepository {
    val content: Observable<List<ContentItemDb>>
    val errors: Observable<Throwable>

    fun onDestroy()
}
