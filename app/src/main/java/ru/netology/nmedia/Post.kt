package ru.netology.nmedia

data class Post(
    val author: String,
    val published: String,
    val content: String,
    var liked: Boolean,
    var likes: Int,
    var share: Int
) {
}