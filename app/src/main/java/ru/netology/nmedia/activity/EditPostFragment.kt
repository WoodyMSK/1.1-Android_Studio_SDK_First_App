package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentEditPostBinding
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged


class EditPostFragment : Fragment() {

    private var defaultPost = Post(
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

        val binding = FragmentEditPostBinding.inflate(inflater, container, false)

        val args: EditPostFragmentArgs by navArgs()

        if (arguments != null ) {
            binding.editText.setText(args.post?.content)
            binding.videoLink.setText(args.post?.videoLink)
        }
        viewModel.getDraft()
            ?.let(binding.editText::setText)

        binding.savePost.setOnClickListener {
            viewModel.changeContentEditPost(
                binding.editText.text.toString(),
                binding.videoLink.text.toString()
            )
            viewModel.save()
            if (args.post != null) { viewModel.removeById(args.post!!.id) }
//            AndroidUtils.hideKeyboard(requireView())

            val action = EditPostFragmentDirections.actionEditPostFragmentToFeedFragment()
            findNavController().navigate(action)

//            val content = binding.editText.text.toString()
//            val link = binding.videoLink.text.toString()
//
//            if (args.post != null ) {
//                viewModel.saveNewEditedPost(args.post!!.copy(content = content, videoLink = link))
//            } else viewModel.saveNewEditedPost(defaultPost.copy(content = content, videoLink = link))

        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.saveDraft(binding.editText.text?.toString())
            isEnabled = false
            requireActivity().onBackPressed()
        }

        return binding.root
    }
}

