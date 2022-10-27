package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostAdapterClickListener
import ru.netology.nmedia.databinding.FragmentEditPostBinding
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import kotlin.math.roundToInt

class PostFragment : Fragment() {
    val viewModel: PostViewModel by viewModels(
        ownerProducer = :: requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentPostBinding.inflate(inflater, container, false)

        val args : PostFragmentArgs by navArgs()

        binding.apply {
            authorTV1.text = args.post.author
            published.text = args.post.published
            postTV.text = args.post.content
            videoContainer.visibility = if (args.post.videoLink == "") View.GONE else View.VISIBLE
            likeButton.text = convert(args.post.likes)
            likeButton.isChecked = args.post.liked
            shareButton.text = convert(args.post.share)


            moreIV.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.option_post)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> {
                                viewModel.removeById(args.post.id)
                                findNavController().popBackStack()
                                true
                            }

                            R.id.edit -> {
                                val action =
                                PostFragmentDirections.actionPostFragmentToEditPostFragment(args.post)
                                findNavController().navigate(action)

                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }
            editButton.setOnClickListener {
                val action =
                    PostFragmentDirections.actionPostFragmentToEditPostFragment(args.post)
                findNavController().navigate(action)
            }

            likeButton.setOnClickListener {
                viewModel.likeById(args.post.id)
                viewModel.data.observe(viewLifecycleOwner) {
                    binding.likeButton.text = viewModel.data.value!!.filter { it.id == args.post.id }.get(0).likes.toString()
                }
            }

            shareButton.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, args.post.content)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, "Share post")
                startActivity(shareIntent)
            }
            videoContainer.setOnClickListener {
                val videoLinkIntent = Intent(Intent.ACTION_VIEW, Uri.parse(args.post.videoLink))
                try {
                    startActivity(videoLinkIntent)
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Некорректный формат ссылки",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
            }

        }
        return binding.root
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