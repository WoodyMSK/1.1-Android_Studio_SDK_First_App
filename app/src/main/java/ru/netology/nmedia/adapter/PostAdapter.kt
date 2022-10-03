package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ItemPostBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.roundToInt

class PostAdapter(
    private val listener: PostAdapterClickListener
) : ListAdapter<Post, PostViewHolder>(PostDiffItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, listener)
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: ItemPostBinding,
    private val listener: PostAdapterClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            authorTV1.text = post.author
            published.text = post.published
            postTV.text = post.content
            videoContainer.visibility = if (post.videoLink == "") View.GONE else View.VISIBLE
            likeButton.text = convert(post.likes)
            likeButton.isChecked = post.liked
            shareButton.text = convert(post.share)
            moreIV.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.option_post)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> {
                                listener.onRemoveClicked(post)
                                true
                            }

                            R.id.edit -> {
                                listener.onEditClickListener(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }
            editButton.setOnClickListener {
                listener.onEditPostActivity(post)
            }
            likeButton.setOnClickListener {
                listener.onLikeClicked(post)
            }
            shareButton.setOnClickListener {
                listener.onShareClicked(post)
            }
            videoContainer.setOnClickListener {
                listener.onVideoLink(post)
            }
        }
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
