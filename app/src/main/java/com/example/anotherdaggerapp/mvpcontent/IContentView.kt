package com.example.anotherdaggerapp.mvpcontent

import com.example.anotherdaggerapp.mvpcontent.model.ContentItemDb

/**
 * Created by vitaly on 2019-07-31.
 */
interface IContentView {
    fun showContent(content: List<ContentItemDb>)

    fun showProgress(isVisible: Boolean)

    fun showError(throwable: Throwable)
}