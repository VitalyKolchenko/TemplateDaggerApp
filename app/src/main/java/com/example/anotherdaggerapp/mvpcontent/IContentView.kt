package com.example.anotherdaggerapp.mvpcontent

import com.example.anotherdaggerapp.data.ContentItem

/**
 * Created by vitaly on 2019-07-31.
 */
interface IContentView {
    fun showContent(content : List<ContentItem>)

    fun showProgress(isVisible: Boolean)

    fun showError(throwable: Throwable)
}