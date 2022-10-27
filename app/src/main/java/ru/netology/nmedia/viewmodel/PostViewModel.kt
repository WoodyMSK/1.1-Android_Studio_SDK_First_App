package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.*

private val defaultPost = Post(
    id = 0L,
    author = "",
    content = "",
    videoLink = "",
    published = "",
    liked = false,
    likes = 0
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    var id: String = "empty"

//    private val repository: PostRepository = PostRepositoryFileImpl(application)
    private val repository: PostRepository = PostRepositorySQLiteImpl(
    AppDb.getInstance(application).getPostDao()
    )
    val data: LiveData<List<Post>> = repository.get()
    val edited = MutableLiveData(defaultPost)
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)

    fun changeContent(content: String) {
        val text = content.trim()
        if (text == edited.value?.content) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun changeContentEditPost(content: String, link: String) {
        val text = content.trim()
        val link = link.trim()
        if (text == edited.value?.content && link == edited.value?.videoLink) {
            return
        }
        edited.value = edited.value?.copy(content = text, videoLink = link)
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = defaultPost
    }

    fun saveNewEditedPost(post: Post) {
        repository.saveNewEditedPost(post)
    }

    fun copyPost(post: Post) {
        repository.copyPost(post)
    }

    fun cancel() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = defaultPost
    }

    fun saveDraft(draft: String?) {
        TODO()
    }

    fun getDraft(): String? {
        TODO()
    }

}