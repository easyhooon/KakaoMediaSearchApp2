package com.kenshi.presentation.mapper

import com.kenshi.domain.entity.blog.BlogEntity
import com.kenshi.domain.entity.image.ImageEntity
import com.kenshi.domain.entity.video.VideoEntity
import com.kenshi.presentation.item.blog.BlogItem
import com.kenshi.presentation.item.image.ImageItem
import com.kenshi.presentation.item.video.VideoItem

internal fun BlogEntity.toItem() = BlogItem(
    title = title,
    contents = contents,
    url = url,
    blogName = blogName,
    thumbnail = thumbnail,
    datetime = datetime,
)


internal fun ImageEntity.toItem() = ImageItem(
    collection = collection,
    thumbnailUrl = thumbnailUrl,
    width = width,
    height = height,
    displaySiteName = displaySiteName,
    docUrl = docUrl,
    datetime = datetime,
)

internal fun VideoEntity.toItem() = VideoItem(
    title = title,
    url = url,
    datetime = datetime,
    playtime = playtime,
    author = author,
    thumbnail = thumbnail,
)