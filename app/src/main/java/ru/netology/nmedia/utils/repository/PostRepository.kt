package ru.netology.nmedia.utils.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.utils.dto.Post

interface PostRepository {
    val data: LiveData<Post>
    fun liking()
    fun sharing()
    fun convert(number: Int): String
}