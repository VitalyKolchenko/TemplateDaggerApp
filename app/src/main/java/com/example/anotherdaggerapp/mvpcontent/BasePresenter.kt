package com.example.anotherdaggerapp.mvpcontent

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V> {
    protected var view: V? = null
    private val viewDisposable = CompositeDisposable()
    private val presenterDisposable = CompositeDisposable()

    open fun attachView(view: V) {
        this.view = view
    }

    open fun detachView() {
        viewDisposable.clear()
        this.view = null
    }

    fun addDisposable(disposable: Disposable) {
        presenterDisposable.add(disposable)
    }

    fun addViewDisposable(disposable: Disposable) {
        viewDisposable.add(disposable)
    }

    open fun onDestroy() {
        presenterDisposable.clear()
        detachView()
    }
}