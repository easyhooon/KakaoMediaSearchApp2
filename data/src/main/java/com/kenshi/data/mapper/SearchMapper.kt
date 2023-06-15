package com.kenshi.data.mapper

import com.kenshi.data.model.blog.Blog
import com.kenshi.data.model.image.Image
import com.kenshi.data.model.video.Video
import com.kenshi.domain.entity.blog.BlogEntity
import com.kenshi.domain.entity.image.ImageEntity
import com.kenshi.domain.entity.video.VideoEntity

internal fun Blog.toEntity() = BlogEntity(
    title = title,
    contents = contents,
    url = url,
    blogName = blogName,
    thumbnail = thumbnail,
    datetime = datetime,
)


internal fun Image.toEntity() = ImageEntity(
    collection = collection,
    thumbnailUrl = thumbnailUrl,
    width = width,
    height = height,
    displaySiteName = displaySiteName,
    docUrl = docUrl,
    datetime = datetime,
)

internal fun Video.toEntity() = VideoEntity(
    title = title,
    url = url,
    datetime = datetime,
    playtime = playtime,
    author = author,
    thumbnail = thumbnail,
)