package com.example.anotherdaggerapp.mvpcontent.model

import com.example.anotherdaggerapp.data.ContentItem
import org.joda.time.DateTime

/**
 * Created by vitaly on 2019-08-17.
 */
class ContentMapper {
    fun map(contentItem: ContentItem): ContentItemDb {
        return ContentItemDb(contentItem.text, DateTime.now().millis, "")
    }
}