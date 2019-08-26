package com.example.anotherdaggerapp.mvpcontent

import android.view.View
import com.example.anotherdaggerapp.data.ContentItem
import com.example.anotherdaggerapp.mvpcontent.model.ContentItemDb
import com.example.anotherdaggerapp.view.BaseViewHolder
import kotlinx.android.synthetic.main.item_content.view.*

/**
 * Created by vitaly on 2019-07-30.
 */


class ContentViewHolder(itemView: View) : BaseViewHolder<ContentItemDb>(itemView) {
    override fun bind(item: ContentItemDb) {
        itemView.text.text = item.title + item.createdTime
    }
}

