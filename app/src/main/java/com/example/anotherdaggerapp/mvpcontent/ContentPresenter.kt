package com.example.anotherdaggerapp.mvpcontent

import com.example.anotherdaggerapp.api.IContentApi
import com.example.anotherdaggerapp.data.ContentItem
import com.example.anotherdaggerapp.utils.async
import io.reactivex.subjects.ReplaySubject

/**
 * Created by vitaly on 2019-07-31.
 */


class ContentPresenter(api: IContentApi) : BasePresenter<IContentView>() {

    private var mDataObservable: ReplaySubject<List<ContentItem>> = ReplaySubject.create(1)

    init {
        api.getContent().async().subscribe(mDataObservable)
    }

    override fun attachView(view: IContentView) {
        super.attachView(view)

        addDisposable(mDataObservable
            .doOnSubscribe { view.showProgress(true) }
            .doOnTerminate { view.showProgress(false) }
            .subscribe(
                { view.showContent(it) },
                { view.showError(it) }
            ))
    }
}