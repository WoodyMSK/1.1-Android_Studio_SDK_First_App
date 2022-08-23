package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonDisposableHandle.parent
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ItemPostBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.roundToInt

class PostAdapter(
    val onPostLiked: (Post) -> Unit,
    val onShareClicked: (Post) -> Unit
) : ListAdapter<Post, PostViewHolder>(PostDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            binding.likeIV.setOnClickListener {
                onPostLiked(getItem(adapterPosition))
            }
            binding.shareIV.setOnClickListener {
                onShareClicked(getItem(adapterPosition))
            }
        }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        with(holder.binding) {
            authorTV1.text = post.author
            published.text = post.published
            postTV.text = post.content
            likedTV.text = convert(post.likes)
            likeIV.setImageResource(
                if (post.liked) R.drawable.ic_red_heart_liked else R.drawable.ic_heart_like
            )
            shareTV.text = convert(post.share)
        }
    }

    object PostDiffItemCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem == newItem

    }

    private fun convert(number: Int): String {
        number.toDouble()
        return when {
            (number < 1000) -> number.toString()
            (number < 1_000_000) -> (((((number / 100.0)).roundToInt()).toDouble()) / 10).toString() + "K"
            else -> (((((number / 100_000.0)).roundToInt()).toDouble()) / 10).toString() + "M"
        }
    }
}