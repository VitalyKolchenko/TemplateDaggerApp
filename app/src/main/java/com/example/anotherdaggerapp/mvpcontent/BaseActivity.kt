package com.example.anotherdaggerapp.mvpcontent

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.anotherdaggerapp.utils.cast
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by vitaly on 2019-07-31.
 */


abstract class BaseActivity<V, P : BasePresenter<V>> : AppCompatActivity() {

    @Inject
    protected lateinit var presenter: P

    override fun onRetainCustomNonConfigurationInstance(): Any = presenter

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        presenter.attachView(this.cast())
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()

        if(isFinishing) presenter.onDestroy()
    }
}