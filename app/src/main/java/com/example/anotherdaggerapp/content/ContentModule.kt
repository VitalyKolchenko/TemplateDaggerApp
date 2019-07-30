package com.example.anotherdaggerapp.content

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.anotherdaggerapp.R
import com.example.anotherdaggerapp.view.BaseRecyclerAdapter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

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


}

@Subcomponent(modules = [ContentModule::class])
interface ContentComponent {
    fun inject(contentActivity: ContentActivity)
}