package com.team.temara.ui.main.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.team.temara.data.remote.response.Result
import com.team.temara.databinding.HomeFragmentBinding
import com.team.temara.ui.main.fragment.profile.ProfileFragmentViewModel
import java.util.Calendar

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModel.HomeViewModelFactory.getInstance(requireContext())
    }

    private var isDataLoaded = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvWelcome.text = getGreeting()

        if(!isDataLoaded) {
            homeViewModel.checkToken().observe(viewLifecycleOwner) { token ->
                if (token != "null") {
                    val myToken = "Bearer $token"
                    homeViewModel.checkId().observe(viewLifecycleOwner) { userId ->
                        val myId = userId ?: ""
                        homeViewModel.getUser(myToken, myId).observe(viewLifecycleOwner) {
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
                                        .into(binding.profileImage)
                                }
                            }
                        }
                    }
                }
            }
            isDataLoaded = true
        }
    }

    fun getGreeting(): String {

        val cal = Calendar.getInstance()

        return when (cal.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Selamat Pagi,"
            in 12..14 -> "Selamat Siang,"
            in 15..18 -> "Selamat Sore,"
            else -> "Selamat Malam, "
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