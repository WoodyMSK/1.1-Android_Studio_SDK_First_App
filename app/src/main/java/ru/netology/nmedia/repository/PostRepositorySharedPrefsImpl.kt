package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositorySharedPrefsImpl(
    context: Context,
) : PostRepository {
    private val gson = Gson()
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "posts"
    private var ids = 1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        prefs.getString(key, null)?.let { it ->
            posts = gson.fromJson(it, type)
            data.value = posts
            ids = if (posts.isNotEmpty()) posts.maxOf { it.id }.inc() else 1L
        }
    }

    override fun get(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = data.value!!.map {
            if (it.id == id) it.copy(
                liked = !it.liked,
                likes = if (it.liked) {
                    it.likes.dec()
                } else {
                    it.likes.inc()
                }
            ) else it
        }
        data.value = posts
        sync()
    }

    override fun shareById(id: Long) {
        posts = data.value!!.map {
            if (it.id == id) it.copy(
                share = it.share + 100
            ) else it
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Long) {
        posts = data.value!!.filter { it.id != id }
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts =
                data.value.orEmpty()
                    .toMutableList()
                    .apply {
                        add(0, post.copy(id = ids++, author = "Me", published = "Now"))
                    }
            data.value = posts
            sync()
            return
        }

        posts = data.value!!.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
        sync()
    }

    override fun saveNewEditedPost(post: Post) {
        if (post.id == 0L) {
            posts =
                data.value.orEmpty()
                    .toMutableList()
                    .apply {
                        add(0, post.copy(id = ids++, author = "Me", published = "Now"))
                    }
            data.value = posts
            sync()
            return
        }

        posts = data.value!!.map {
            if (it.id != post.id) it else it.copy(content = post.content, videoLink = post.videoLink)
        }
        data.value = posts
        sync()
    }


    override fun copyPost(post: Post) {
        posts =
            data.value.orEmpty()
                .toMutableList()
                .apply {
                    add(
                        0, post.copy(
                            id = post.id,
                            author = post.author,
                            published = post.published,
                            content = post.content,

                            liked = post.liked,
                            likes = post.likes,
                            share = post.share
                        )
                    )
                }
        data.value = posts
        sync()
        return
    }

    override fun getDraft(): String? {
        TODO("Not yet implemented")
    }

    override fun setDraft(draft: String?) {
        TODO("Not yet implemented")
    }

    override fun cancel(post: Post) {
        posts = data.value!!.map {
            if (it.id != post.id) it else it.copy(content = it.content)
        }
        data.value = posts
        sync()
    }

    private fun sync() {
        with(prefs.edit()) {
            putString(key, gson.toJson(this@PostRepositorySharedPrefsImpl.posts))
            apply()
        }
    }
}