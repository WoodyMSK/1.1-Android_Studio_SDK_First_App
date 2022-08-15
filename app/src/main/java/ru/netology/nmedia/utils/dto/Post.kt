package ru.netology.nmedia.utils.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val liked: Boolean = false,
    val likes: Int = 0,
    val share: Int = 0
) {

}

