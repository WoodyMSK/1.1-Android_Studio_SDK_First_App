package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgument


class NewPostFragment : Fragment() {
    private var postState = "empty"
    private var post = Post(
        id = 0L,
        author = "author1",
        published = "published1",
        content = "content1",

        videoLink = "videoLink1",
        liked = false,
        likes = 0,
        share = 0
    )

    val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)


        binding.savePost.setOnClickListener {

            val content = binding.editText.text.toString()
            val link = binding.videoLink.text.toString()
            viewModel.saveNewEditedPost(post.copy(content = content, videoLink = link)
            )
            findNavController().popBackStack()
        }

        return binding.root
    }
}





