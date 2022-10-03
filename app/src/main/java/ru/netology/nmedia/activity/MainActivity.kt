package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.AndroidUtils.hideKeyboard
import ru.netology.nmedia.Constance.ADD_EDIT_STATE
import ru.netology.nmedia.Constance.ADD_POST
import ru.netology.nmedia.Constance.ADD_POST_COMPLETE
import ru.netology.nmedia.Constance.EDIT_POST
import ru.netology.nmedia.Constance.EDIT_POST_COMPLETE
import ru.netology.nmedia.Constance.POST_CONTENT
import ru.netology.nmedia.Constance.REQUEST_CODE_EDIT_POST
import ru.netology.nmedia.Constance.REQUEST_CODE_NEW_POST
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostAdapterClickListener
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
                    val intent = Intent(this@MainActivity, NewPostActivity::class.java)
                    intent.putExtra(ADD_EDIT_STATE, EDIT_POST)
                    intent.putExtra(POST_CONTENT, post)
                    startActivityForResult(intent, REQUEST_CODE_EDIT_POST)
                }

                override fun onVideoLink(post: Post) {
                    val videoLinkIntent = Intent(ACTION_VIEW, Uri.parse(post.videoLink))
                    try {
                        startActivity(videoLinkIntent)
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, "Некорректный формат ссылки", Toast.LENGTH_SHORT).show()
                        return
                    }


                }

            }
        )
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        viewModel.edited.observe(this) {
            binding.content.setText(it.content)
            if (it.content.isNotBlank()) {
                binding.content.requestFocus()
                binding.editGroup.visibility = View.VISIBLE
            }
        }



        binding.save.setOnClickListener { it ->
            val text = binding.content.text?.toString().orEmpty()
            if (text.isBlank()) {
                Toast.makeText(this, "Empty post!", Toast.LENGTH_SHORT).show()
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
            val intent = Intent(this@MainActivity, NewPostActivity::class.java)
            intent.putExtra(ADD_EDIT_STATE, ADD_POST)
            startActivityForResult(intent, REQUEST_CODE_NEW_POST)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_NEW_POST -> {
                if (resultCode != Activity.RESULT_OK) {
                    return
                }

                (data?.getSerializableExtra(ADD_POST_COMPLETE) as Post).let {
                    println(it)
                    viewModel.saveNewEditedPost(it)
                }
            }

            REQUEST_CODE_EDIT_POST -> {
                if (resultCode != Activity.RESULT_OK) {
                    return
                }
                (data?.getSerializableExtra(EDIT_POST_COMPLETE) as Post).let {
                    viewModel.saveNewEditedPost(it)
                }
            }
        }
    }
}











