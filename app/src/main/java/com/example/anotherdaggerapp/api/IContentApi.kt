package com.example.anotherdaggerapp.api

import com.example.anotherdaggerapp.data.ContentItem
import io.reactivex.Observable

/**
 * Created by vitaly on 2019-07-31.
 */
interface IContentApi {
    fun getContent(): Observable<List<ContentItem>>
}