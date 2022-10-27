package ru.netology.nmedia.activity

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.util.AndroidUtils.hideKeyboard
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostAdapterClickListener
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {
    val viewModel: PostViewModel by viewModels(
        ownerProducer = :: requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = PostAdapter(
            object : PostAdapterClickListener {
                override fun onEditClickListener(post: Post) {
                    viewModel.edit(post)
                }

                override fun onRemoveClicked(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onLikeClicked(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShareClicked(post: Post) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(intent, "Share post")
                    startActivity(shareIntent)
                }

                override fun onEditPostActivity(post: Post) {
                    val action =
                        FeedFragmentDirections.actionFeedFragmentToEditPostFragment(post)

                    findNavController().navigate(action)

                }

                override fun onVideoLink(post: Post) {
                    val videoLinkIntent = Intent(ACTION_VIEW, Uri.parse(post.videoLink))
                    try {
                        startActivity(videoLinkIntent)
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "Некорректный формат ссылки",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                }

                override fun onPostClicked(post: Post) {
                    val action =
                        FeedFragmentDirections.actionFeedFragmentToPostFragment(post)

                    findNavController().navigate(action)

                }

            }
        )
        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(viewLifecycleOwner) {
            binding.content.setText(it.content)
            if (it.content.isNotBlank()) {
                binding.content.requestFocus()
                binding.editGroup.visibility = View.VISIBLE
            }
        }


        binding.save.setOnClickListener { it ->
            val text = binding.content.text?.toString().orEmpty()
            if (text.isBlank()) {
                Toast.makeText(context, "Empty post!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.changeContent(text)
            viewModel.save()
            binding.content.clearFocus()
            binding.editGroup.visibility = View.GONE
            it.hideKeyboard()

        }

        binding.cancelButtonIV.setOnClickListener { it ->
            binding.content.clearFocus()
            binding.editGroup.visibility = View.GONE
            it.hideKeyboard()
            viewModel.cancel()
        }

        binding.fab.setOnClickListener {

            val action = FeedFragmentDirections.actionFeedFragmentToEditPostFragment(null)
            findNavController().navigate(action)
        }

        return binding.root
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            REQUEST_CODE_NEW_POST -> {
//                if (resultCode != Activity.RESULT_OK) {
//                    return
//                }
//
//                (data?.getSerializableExtra(ADD_POST_COMPLETE) as Post).let {
//                    println(it)
//                    viewModel.saveNewEditedPost(it)
//                }
//            }
//
//            REQUEST_CODE_EDIT_POST -> {
//                if (resultCode != Activity.RESULT_OK) {
//                    return
//                }
//                (data?.getSerializableExtra(EDIT_POST_COMPLETE) as Post).let {
//                    viewModel.saveNewEditedPost(it)
//                }
//            }
//        }
//    }
}











