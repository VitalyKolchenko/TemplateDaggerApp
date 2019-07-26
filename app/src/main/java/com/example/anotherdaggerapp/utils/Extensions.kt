package com.example.anotherdaggerapp.utils

import android.content.Context
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by vitaly on 2019-07-26.
 */
@Suppress("UNCHECKED_CAST")
fun <T> Any.cast() = this as T

fun <T> Observable<T>.async(): Observable<T> =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun Context.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()