package ru.netology.nmedia.activity

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import ru.netology.nmedia.Constance.EDIT_POST_COMPLETE
import ru.netology.nmedia.Constance.POST_CONTENT
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityIntentHandlerBinding
import ru.netology.nmedia.dto.Post

class IntentHandlerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editTextHA.requestFocus();
        val post = intent.getSerializableExtra(POST_CONTENT) as Post
        binding.editTextHA.text = Editable.Factory.getInstance().newEditable(post.content)

        binding.savePostHA.setOnClickListener {
            intent.putExtra(EDIT_POST_COMPLETE, post.copy(content = binding.editTextHA.text.toString()))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}