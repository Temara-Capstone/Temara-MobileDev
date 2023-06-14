package com.team.temara.ui.main.fragment.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.team.temara.data.remote.response.Result
import com.team.temara.databinding.ChatFragmentBinding
import com.team.temara.ui.chat.ChatActivity
import com.team.temara.ui.info.InfoUserActivity
import com.team.temara.ui.profil.personaldata.PersonalDataActivity

class ChatFragment : Fragment() {
    private var _binding: ChatFragmentBinding? = null
    private val binding get() = _binding!!

    private var isDataLoaded = false



    private val chatFragmentViewModel: ChatFragmentViewModel by viewModels {
        ChatFragmentViewModel.ChatFragmentViewModelFactory.getInstance(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChatFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            btnInformation.setOnClickListener {
                startActivity(Intent(context, InfoUserActivity::class.java))
            }
            cvBot.setOnClickListener {
                startActivity(Intent(context, ChatActivity::class.java))
            }
            ivUser.setOnClickListener {
                startActivity(Intent(context, PersonalDataActivity::class.java))
            }
        }


        if(!isDataLoaded) {
        chatFragmentViewModel.checkToken().observe(viewLifecycleOwner) { token ->
            if (token != "null") {
                val myToken = "Bearer $token"
                chatFragmentViewModel.checkId().observe(viewLifecycleOwner) { userId ->
                    val myId = userId ?: ""
                    chatFragmentViewModel.getUser(myToken, myId).observe(viewLifecycleOwner) {
                            when (it) {
                                is Result.Loading -> {
                                }

                                is Result.Error -> {
                                }

                                is Result.Success -> {
                                    val result = it.result
                                    binding.tvName.text = result.name

                                    Glide.with(requireContext())
                                        .load(result.image)
                                        .into(binding.ivUser)
                                }
                            }
                        }
                    }
                }
            }
            isDataLoaded = true
        }
    }

    override fun onResume() {
        super.onResume()
        isDataLoaded = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}