package com.example.anotherdaggerapp.mvpcontent

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.anotherdaggerapp.DbModule
import com.example.anotherdaggerapp.PerActivity
import com.example.anotherdaggerapp.R
import com.example.anotherdaggerapp.mvpcontent.model.ContentItemDb
import com.example.anotherdaggerapp.mvpcontent.model.ContentMapper
import com.example.anotherdaggerapp.mvpcontent.model.ContentRepositoryImpl
import com.example.anotherdaggerapp.mvpcontent.model.IContentRepository
import com.example.anotherdaggerapp.utils.cast
import com.example.anotherdaggerapp.view.BaseRecyclerAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Singleton

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
    @PerActivity
    fun mapper() = ContentMapper()

    @Provides
    fun presenter(contentRepo: IContentRepository) =
        contentActivity.lastCustomNonConfigurationInstance?.cast() ?: ContentPresenter(contentRepo)

}

@Module
abstract class ContentBindingModule {
    @Binds
    @PerActivity
    abstract fun contentRepo(contentRepo: ContentRepositoryImpl): IContentRepository
}

@Subcomponent(modules = [ContentModule::class, ContentBindingModule::class, DbModule::class])
@PerActivity
interface ContentComponent {
    fun inject(contentActivity: ContentActivity)
}