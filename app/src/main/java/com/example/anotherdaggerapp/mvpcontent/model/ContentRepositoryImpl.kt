package com.example.anotherdaggerapp.mvpcontent.model

import com.example.anotherdaggerapp.api.IContentApi
import com.example.anotherdaggerapp.utils.async
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.realm.Realm
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(
    private var api: IContentApi,
    private val realm: Realm,
    private val mapper: ContentMapper
) : IContentRepository {

    override val content: Observable<List<ContentItemDb>>
        get() {
            return api.getContent().map { list -> list.map(mapper::map) }
                .async()
                .doOnNext { list ->
                    realm.executeTransaction {
                        it.delete(ContentItemDb::class.java)
                        it.insert(list)
                    }
                }.onErrorResumeNext { t: Throwable ->
                    errors.onNext(t)
                    realm.where(ContentItemDb::class.java).findAllAsync().asFlowable()
                        .filter { it.isLoaded }.toObservable()
                }
        }

    override val errors = PublishSubject.create<Throwable>()

    override fun onDestroy() {
        realm.close()
    }
}