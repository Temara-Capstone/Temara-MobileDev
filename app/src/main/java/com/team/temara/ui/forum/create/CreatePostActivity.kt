package com.team.temara.ui.forum.create

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team.temara.data.remote.response.Result
import com.team.temara.databinding.ForumCreatePostActivityBinding
import com.team.temara.ui.main.fragment.forum.ForumFragment
import com.team.temara.utils.uriToFile
import java.io.File

class CreatePostActivity : AppCompatActivity() {

    private var getUserFile: File? = null

    private val binding : ForumCreatePostActivityBinding by lazy {
        ForumCreatePostActivityBinding.inflate(layoutInflater)
    }

    private val createPostViewModel : CreatePostViewModel by viewModels {
        CreatePostViewModel.CreatePostViewModelFactory.getInstance(this)
    }

    private var file: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnGallery.setOnClickListener {
            openGallery()
        }

        binding.btnPost.setOnClickListener {
            createPostViewModel.checkToken().observe(this) { token ->
                if (token != "null") {
                    val myToken = "Bearer $token"
                    createPostViewModel.checkId().observe(this) { userId ->
                        postForum(myToken, userId)
                    }
                }
            }
        }
    }

    private fun postForum(token: String, userId: String) {
        if (binding.etDesc.text.isNullOrEmpty()) {
            Toast.makeText(this, "Tolong diisi statusnya dulu yah kak :)", Toast.LENGTH_SHORT).show()
        } else {
            val desc = binding.etDesc.text.toString()

            if (file != null) {
                createPostViewModel.postForum(
                    token,
                    userId,
                    file,
                    desc
                ).observe(this) { result ->
                    when (result) {
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, "Sukses", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            val error = result.error
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                        }
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            } else {
                createPostViewModel.postForum(
                    token,
                    userId,
                    null,
                    desc
                ).observe(this) { result ->
                    when (result) {
                        is Result.Success -> {
                            Toast.makeText(this, "Sukses", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        is Result.Error -> {
                            val error = result.error
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                        }
                        is Result.Loading -> {

                        }
                    }
                }
            }
        }
    }

    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == RESULT_OK) {
            val imgChoose: Uri = result.data?.data as Uri
            val file = uriToFile(imgChoose, this)
            getUserFile = file
            binding.ivImageDesc.setImageURI(imgChoose)
            this.file = file
        }
    }

    private fun openGallery() {
        val galIntent = Intent()
        galIntent.action = Intent.ACTION_GET_CONTENT
        galIntent.type = "image/*"
        val choose = Intent.createChooser(galIntent, "Select Photo")
        launchGallery.launch(choose)
    }
}
