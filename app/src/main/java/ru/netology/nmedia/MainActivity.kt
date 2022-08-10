package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.BigDecimal
import kotlin.math.round
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))

//        val binding: ActivityMainBinding =
//            DataBindingUtil.setContentView(this, R.layout.activity_main)

        setContentView(binding.root)

        val post: Post = Post(
            getString(R.string.authorName),
            getString(R.string.published),
            getString(R.string.postTV),
            liked = false,
            likes = 999,
            share = 999
        )



        with(binding) {



            authorTV1.text = post.author
            published.text = post.published

            shareIV.setOnClickListener { shareTV.text = sharing(post) }

            likeIV.setOnClickListener {
                post.liked = !post.liked
                likeIV.setImageResource(liking(post))
                likedTV.text = addLike(post)
            }

        }


    }

}

private fun convert(number: Int): String {
//    return number.toString()
    number.toDouble()
    return when {
        (number < 1000) -> number.toString()
        (number < 1_000_000) -> (((((number / 100.0)).roundToInt()).toDouble())/10).toString()+"K"
        else -> (((((number / 100_000.0)).roundToInt()).toDouble())/10).toString()+"M"
    }
}



//private fun sharing(post: Post) = convert(post.share)

private fun sharing(post: Post): String {
    val x = post.share
    post.share = x + 100_000
    return convert(post.share)
}

private fun liking(post: Post) =
    if (post.liked) R.drawable.ic_red_heart_liked else R.drawable.ic_heart_like

private fun addLike(post: Post) = if (post.liked) convert(post.likes++) else convert(post.likes--)

//    private fun getImageResource(post: Post) =
//        if (post.liked) {
//            R.drawable.ic_heart_like
//
//        }
//            else R.drawable.ic_red_heart_liked






