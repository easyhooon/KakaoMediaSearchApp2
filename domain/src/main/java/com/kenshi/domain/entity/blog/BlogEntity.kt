package com.kenshi.domain.entity.blog

data class BlogEntity(
    val title: String,
    val contents: String,
    val url: String,
    val blogName: String,
    val thumbnail: String,
    val datetime: String,
)
