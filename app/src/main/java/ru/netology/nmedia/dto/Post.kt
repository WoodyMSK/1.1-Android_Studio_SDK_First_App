package ru.netology.nmedia.dto

import java.io.Serializable

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val videoLink: String = "",
    var liked: Boolean = false,
    val likes: Int = 0,
    val share: Int = 0
) : Serializable {

}

