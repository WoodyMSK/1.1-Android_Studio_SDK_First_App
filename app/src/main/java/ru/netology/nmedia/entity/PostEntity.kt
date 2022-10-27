package ru.netology.nmedia.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val videoLink: String = "",
    val liked: Boolean = false,
    val likes: Int = 0,
    val share: Int = 0,
) {

    fun toDto(): Post =
        Post(
            id = id,
            author = author,
            published = published,
            content = content,
            videoLink = videoLink,
            liked = liked,
            likes = likes,
            share = share
        )

    companion object {
        fun fromDto(post: Post): PostEntity =
            with(post) {
                PostEntity(
                    id = id,
                    author = author,
                    published = published,
                    content = content,
                    videoLink = videoLink,
                    liked = liked,
                    likes = likes,
                    share = share
                )
            }
    }
}