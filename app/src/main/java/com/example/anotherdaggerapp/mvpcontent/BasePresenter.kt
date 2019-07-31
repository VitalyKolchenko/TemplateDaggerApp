package com.example.anotherdaggerapp.mvpcontent

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V> {
    protected var view: V? = null
    private val viewDisposable = CompositeDisposable()

    open fun attachView(view: V) {
        this.view = view
    }

    open fun detachView() {
        viewDisposable.clear()
        this.view = null
    }

    fun addDisposable(disposable: Disposable) {
        viewDisposable.add(disposable)
    }

    fun onDestroy() {
        detachView()
    }
}