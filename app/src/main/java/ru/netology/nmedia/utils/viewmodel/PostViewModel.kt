package ru.netology.nmedia.utils.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.utils.dto.Post
import ru.netology.nmedia.utils.repository.PostRepositoryInMemory

class PostViewModel: ViewModel() {

    private val repository = PostRepositoryInMemory()
    val data: LiveData<Post>
        get() = repository.data

    fun liking() {
        repository.liking()
    }

    fun sharing() {
        repository.sharing()
    }

}