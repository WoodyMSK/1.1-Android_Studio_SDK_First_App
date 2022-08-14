package ru.netology.nmedia.utils.repository

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.utils.dto.Post

class PostRepositoryInMemory : PostRepository {

    private val post: Post = Post(
        id = 1L,
        author = "Нетология. Университет интернет-проффесий будущего",
        published = "21 мая в 18:36",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен ➞ https://netology.ru"
    )

    override val data = MutableLiveData<Post>(post)

    override fun liking() {
        val currentPost = data.value!!
        val newPost = currentPost.copy(
            liked = !currentPost.liked,
            likes = if (currentPost.liked) {
                currentPost.likes.dec()

            } else {
                currentPost.likes.inc()

            })

        data.value = newPost
    }

//    private fun liking(post: Post) =
//        if (post.liked) R.drawable.ic_red_heart_liked else R.drawable.ic_heart_like

    override fun sharing() {
        val currentPost = data.value!!
        val newPost = currentPost.copy(
            share = currentPost.share + 100
        )

        data.value = newPost
    }

    override fun convert(number: Int): String {
        TODO("Not yet implemented")
    }


}