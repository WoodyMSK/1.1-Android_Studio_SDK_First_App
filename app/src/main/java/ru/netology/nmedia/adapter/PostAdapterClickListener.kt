package ru.netology.nmedia.adapter

import ru.netology.nmedia.dto.Post

interface PostAdapterClickListener {
    fun onEditClickListener(post: Post)
    fun onRemoveClicked(post: Post)
    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onEditPostActivity(post: Post)
    fun onVideoLink(post: Post)

}