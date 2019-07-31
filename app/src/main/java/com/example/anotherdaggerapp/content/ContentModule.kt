package com.example.anotherdaggerapp.content

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.anotherdaggerapp.R
import com.example.anotherdaggerapp.api.IContentApi
import com.example.anotherdaggerapp.data.ContentItem
import com.example.anotherdaggerapp.utils.cast
import com.example.anotherdaggerapp.view.BaseRecyclerAdapter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by vitaly on 2019-07-30.
 */
@Module
class ContentModule(private val contentActivity: ContentActivity) {

    @Provides
    fun inflater(): LayoutInflater = contentActivity.layoutInflater

    @Provides
    fun vhFactory(inflater: LayoutInflater): (ViewGroup) -> ContentViewHolder = {
        ContentViewHolder(inflater.inflate(R.layout.item_content, it, false))
            .apply { itemView.setOnClickListener { contentActivity.onItemClick(adapterPosition) } }
    }

    @Provides
    @JvmSuppressWildcards
    fun provideRecyclerAdapter(factory: (ViewGroup) -> ContentViewHolder) =
        BaseRecyclerAdapter(factory)


    @Provides
    fun contentApi(): IContentApi = object : IContentApi {
        override fun getContent(): Observable<List<ContentItem>> {
            return Observable.just((0..50).map { ContentItem("Item $it") }).delay(3L, TimeUnit.SECONDS)
        }
    }

    @Provides
    fun viewModel(contentApi: IContentApi): IContentViewModel {
        return ViewModelProviders.of(contentActivity, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T = ContentViewModel(contentApi).cast()
        }
        ).get(ContentViewModel::class.java)
    }
}

@Subcomponent(modules = [ContentModule::class])
interface ContentComponent {
    fun inject(contentActivity: ContentActivity)
}