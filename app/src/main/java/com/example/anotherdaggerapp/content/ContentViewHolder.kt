package com.example.anotherdaggerapp.content

import android.view.View
import com.example.anotherdaggerapp.data.ContentItem
import com.example.anotherdaggerapp.view.BaseViewHolder
import kotlinx.android.synthetic.main.item_content.view.*

/**
 * Created by vitaly on 2019-07-30.
 */


class ContentViewHolder(itemView: View) : BaseViewHolder<ContentItem>(itemView) {
    override fun bind(item: ContentItem) {
        itemView.text.text = item.text
    }
}

