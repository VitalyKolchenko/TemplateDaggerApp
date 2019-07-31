package com.example.anotherdaggerapp.mvpcontent

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.anotherdaggerapp.R
import com.example.anotherdaggerapp.api.IContentApi
import com.example.anotherdaggerapp.content.ContentViewHolder
import com.example.anotherdaggerapp.utils.cast
import com.example.anotherdaggerapp.view.BaseRecyclerAdapter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Created by vitaly on 2019-07-31.
 */

@Module
class ContentModule(val contentActivity: ContentActivity) {

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
    fun presenter(contentApi: IContentApi) =
        contentActivity.lastCustomNonConfigurationInstance?.cast() ?: ContentPresenter(contentApi)

}

@Subcomponent(modules = [ContentModule::class])
interface ContentComponent {
    fun inject(contentActivity: ContentActivity)
}