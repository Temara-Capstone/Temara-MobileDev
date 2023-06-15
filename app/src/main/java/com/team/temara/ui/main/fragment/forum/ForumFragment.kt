package com.team.temara.ui.main.fragment.forum

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.team.temara.data.remote.response.Result
import com.team.temara.databinding.ForumFragmentBinding
import com.team.temara.ui.main.fragment.home.HomeViewModel
import com.team.temara.ui.profil.personaldata.PersonalDataActivity

class ForumFragment : Fragment() {
    private var _binding: ForumFragmentBinding? = null
    private val binding get() = _binding!!

    private val forumViewModel: ForumViewModel by viewModels {
        ForumViewModel.ForumViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ForumFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forumViewModel.checkToken().observe(viewLifecycleOwner) { token ->
            if (token != "null") {
                val myToken = "Bearer $token"
                forumViewModel.checkId().observe(viewLifecycleOwner) { userId ->
                    val myId = userId ?: ""
                    forumViewModel.getUser(myToken, myId).observe(viewLifecycleOwner) {
                        when (it) {
                            is Result.Loading -> {
                            }

                            is Result.Error -> {
                            }

                            is Result.Success -> {
                                val result = it.result
                                Glide.with(requireContext())
                                    .load(result.image)
                                    .into(binding.profileImage)
                            }
                        }
                    }
                }
            }
        }

        binding.apply {
            profileImage.setOnClickListener {
                startActivity(Intent(context, PersonalDataActivity::class.java))
            }
        }

    }
}