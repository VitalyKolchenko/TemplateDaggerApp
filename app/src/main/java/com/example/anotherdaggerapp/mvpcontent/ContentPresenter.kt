package com.example.anotherdaggerapp.mvpcontent

import com.example.anotherdaggerapp.mvpcontent.model.ContentItemDb
import com.example.anotherdaggerapp.mvpcontent.model.IContentRepository
import io.reactivex.subjects.ReplaySubject

/**
 * Created by vitaly on 2019-07-31.
 */


class ContentPresenter(private var contentRepository: IContentRepository) : BasePresenter<IContentView>() {

    private val mDataObservable: ReplaySubject<List<ContentItemDb>> = ReplaySubject.create(1)

    init {
        //loadContent()
    }

    fun loadContent() {
        addDisposable(contentRepository.content.subscribe { mDataObservable.onNext(it) })
    }

    override fun attachView(view: IContentView) {
        super.attachView(view)

        addViewDisposable(mDataObservable
            .doOnSubscribe { view.showProgress(true) }
            .doAfterNext { view.showProgress(false) }
            .subscribe { view.showContent(it) })

        addViewDisposable(
            contentRepository.errors.subscribe { view.showError(it) })
    }

    override fun onDestroy() {
        super.onDestroy()
        contentRepository.onDestroy()
    }
}