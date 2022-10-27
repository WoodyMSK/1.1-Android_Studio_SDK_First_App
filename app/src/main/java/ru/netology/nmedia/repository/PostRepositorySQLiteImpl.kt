package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

class PostRepositorySQLiteImpl(
    private val dao: PostDao
): PostRepository {

    override fun get(): LiveData<List<Post>> = dao.get().map {
        it.map(PostEntity::toDto)
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun cancel(post: Post) {
        TODO("Not yet implemented")
    }

    override fun copyPost(post: Post) {
        TODO("Not yet implemented")
    }

    override fun saveNewEditedPost(post: Post) {
        TODO("Not yet implemented")
    }

    override fun shareById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun setDraft(draft: String?) {
        TODO("Not yet implemented")
    }

    override fun getDraft(): String? {
        TODO("Not yet implemented")
    }
}