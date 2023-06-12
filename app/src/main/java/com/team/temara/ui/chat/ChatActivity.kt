package com.team.temara.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team.temara.R
import com.team.temara.adapter.ChatAdapter
import com.team.temara.adapter.RecommendedQuestionsAdapter
import com.team.temara.databinding.ChatActivityBinding
import com.team.temara.utils.Message
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val messageList: MutableList<Message> = ArrayList()
    private lateinit var chatAdapter: ChatAdapter

    private val client = OkHttpClient()

    private val binding: ChatActivityBinding by lazy {
        ChatActivityBinding.inflate(layoutInflater)
    }

    private val recommendedQuestions = listOf(
        "Hai!",
        "Aku Kesepian",
        "Aku merasa tidak ada yang suka denganku",
        "Aku Benci diriku sendiri",
        "Kamu siapa?"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setup()

        binding.apply {
            binding.tvName.text = getString(R.string.ara)
            ivUser.setImageResource(R.drawable.ara)
        }
    }

    private fun setup() {
        recyclerView = binding.rvChat
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager

        chatAdapter = ChatAdapter(messageList)
        recyclerView.adapter = chatAdapter

        binding.btnSend.setOnClickListener {
            val question = binding.etComment.text.toString()
            if (question.isNotEmpty()) { // Menambahkan pengecekan jika chat masih kosong
                addToChat(question, Message.SEND_BY_ME)
                binding.etComment.setText("")
                callAPI(question)
            }
        }

        showRecommendedQuestions()
    }

    private fun showRecommendedQuestions() {
        val recommendedQuestionsAdapter = RecommendedQuestionsAdapter(recommendedQuestions, object : RecommendedQuestionsAdapter.OnItemClickListener {
            override fun onItemClick(question: String) {
                addToChat(question, Message.SEND_BY_ME)
                binding.etComment.setText("")
                callAPI(question)
            }
        })
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecom.layoutManager = layoutManager
        binding.rvRecom.adapter = recommendedQuestionsAdapter
    }

    private fun addToChat(message: String, sendBy: String) {
        runOnUiThread {
            messageList.add(Message(message, sendBy))
            chatAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(chatAdapter.itemCount)
        }
    }

    private fun addResponse(response: String) {
        runOnUiThread {
            messageList.removeAt(messageList.size - 1)
            addToChat(response, Message.SEND_BY_BOT)
        }
    }

    private fun callAPI(question: String) {
        messageList.add(Message(getString(R.string.write), Message.SEND_BY_BOT))
        chatAdapter.notifyDataSetChanged()
        recyclerView.smoothScrollToPosition(chatAdapter.itemCount)

        val requestBody: RequestBody = FormBody.Builder()
            .add("user", question)
            .build()

        val request: Request = Request.Builder()
            .url("https://index-f4eirp3daa-as.a.run.app/bot")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val errorMessage = "Failed to load response due to ${e.message}"
                addResponse(errorMessage)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        try {
                            val responseBody = response.body?.string()
                            val jsonObject = responseBody?.let { JSONObject(it) }
                            val botResponse = jsonObject?.optString("Bot")
                            if (!botResponse.isNullOrEmpty()) {
                                addResponse(botResponse.trim())
                            } else {
                                val emptyResponseMessage = "Empty response from the server"
                                addResponse(emptyResponseMessage)
                            }
                        } catch (e: JSONException) {
                            val errorMessage = "Failed to parse response json"
                            addResponse(errorMessage)
                            Log.d("MainActivity", "Error parsing JSON: " + e.message)
                        }
                    } else {
                        val errorBody = response.body?.string()
                        val errorMessage = "Failed to load response due to $errorBody"
                        addResponse(errorMessage)
                        Log.d("MainActivity", "Error: $errorBody")
                    }
                }
            }
        })
    }
}
