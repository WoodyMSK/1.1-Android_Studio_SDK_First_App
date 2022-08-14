package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.utils.dto.Post
import ru.netology.nmedia.utils.viewmodel.PostViewModel
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        with(binding) {

            likeIV.setOnClickListener {
                viewModel.liking()
            }

            shareIV.setOnClickListener {
                viewModel.sharing()
            }

            viewModel.data.observe(this@MainActivity) { post ->

                authorTV1.text = post.author
                published.text = post.published
                postTV.text = post.content
                likedTV.text = convert(post.likes)
                likeIV.setImageResource(liking(post))
                shareTV.text = convert(post.share)

            }
        }
    }
}

private fun convert(number: Int): String {
    number.toDouble()
    return when {
        (number < 1000) -> number.toString()
        (number < 1_000_000) -> (((((number / 100.0)).roundToInt()).toDouble())/10).toString()+"K"
        else -> (((((number / 100_000.0)).roundToInt()).toDouble())/10).toString()+"M"
    }
}

private fun liking(post: Post) =
    if (post.liked) R.drawable.ic_red_heart_liked else R.drawable.ic_heart_like









