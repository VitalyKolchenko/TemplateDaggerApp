package com.example.anotherdaggerapp.mvpcontent.model

import io.realm.RealmModel
import io.realm.annotations.RealmClass
import org.joda.time.DateTime

/**
 * Created by vitaly on 2019-08-17.
 */
@RealmClass
open class ContentItemDb(
    var title: String = "",
    var createdTimesamp: Long? = null,
    var userName: String = ""
) : RealmModel {
    val createdTime: DateTime
        get() = DateTime(createdTimesamp)

}