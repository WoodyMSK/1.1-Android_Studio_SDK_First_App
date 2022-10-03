package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import ru.netology.nmedia.Constance.ADD_EDIT_STATE
import ru.netology.nmedia.Constance.ADD_POST
import ru.netology.nmedia.Constance.ADD_POST_COMPLETE
import ru.netology.nmedia.Constance.EDIT_POST
import ru.netology.nmedia.Constance.EDIT_POST_COMPLETE
import ru.netology.nmedia.Constance.POST_CONTENT
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.dto.Post

class NewPostActivity : AppCompatActivity() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editText.requestFocus();

        postState = intent.getStringExtra(ADD_EDIT_STATE)!!
        if (postState == EDIT_POST) {
            post = intent.getSerializableExtra(POST_CONTENT) as Post
            binding.editText.text = Editable.Factory.getInstance().newEditable(post.content)
            binding.videoLink.text = Editable.Factory.getInstance().newEditable(post.videoLink)
        }


        binding.savePost.setOnClickListener {
            val intent = Intent()

            when (postState) {
                ADD_POST -> {
                    if (binding.editText.text.isNullOrBlank()) {
                        setResult(Activity.RESULT_CANCELED, intent)
                    } else {
                        intent.putExtra(ADD_POST_COMPLETE, post.copy(content = binding.editText.text.toString(), videoLink = binding.videoLink.text.toString()))
                        setResult(Activity.RESULT_OK, intent)
                    }
                    finish()
                }

                EDIT_POST -> {
                    if (binding.editText.text.isNullOrBlank()) {
                        setResult(Activity.RESULT_CANCELED, intent)
                    } else {
                        intent.putExtra(EDIT_POST_COMPLETE, post.copy(content = binding.editText.text.toString(), videoLink = binding.videoLink.text.toString()))
                        setResult(Activity.RESULT_OK, intent)
                    }
                    finish()
                }
            }
        }
    }
}

