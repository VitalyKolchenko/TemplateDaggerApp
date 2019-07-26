package com.example.anotherdaggerapp

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

enum class Status {
    SUCCESS, ERROR, LOADING
}

class Result<T>(
    val data: T? = null,
    val status: Status,
    val error: Throwable? = null
)

class LceLiveData<T> : MutableLiveData<Result<T>>() {

    private fun setError(t: Throwable) {
        value = Result(error = t, status = Status.ERROR)
    }

    private fun setData(data: T?) {
        value = Result(data, Status.SUCCESS)
    }

    private fun setLoading() {
        value = Result(status = Status.LOADING)
    }


    fun rxObserver(): Observer<T> = object : Observer<T> {
        override fun onComplete() {

        }

        override fun onSubscribe(d: Disposable) {
            setLoading()
        }

        override fun onNext(t: T) {
            setData(t)
        }

        override fun onError(e: Throwable) {
            setError(e)
        }
    }
}
