package com.example.anotherdaggerapp.content

import androidx.lifecycle.ViewModel
import com.example.anotherdaggerapp.LceLiveData
import com.example.anotherdaggerapp.api.IContentApi
import com.example.anotherdaggerapp.data.ContentItem
import com.example.anotherdaggerapp.utils.async

/**
 * Created by vitaly on 2019-07-31.
 */

interface IContentViewModel{
    val content: LceLiveData<List<ContentItem>>
}

class ContentViewModel(
    private val contentApi: IContentApi
) : ViewModel(), IContentViewModel {
    override val content: LceLiveData<List<ContentItem>> = LceLiveData()

    init {
        contentApi.getContent().async().subscribe(content.rxObserver())
    }
}