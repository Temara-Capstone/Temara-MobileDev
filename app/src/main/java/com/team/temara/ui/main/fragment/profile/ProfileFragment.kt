package com.team.temara.ui.main.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.team.temara.data.remote.response.Result
import com.team.temara.data.remote.response.resultData
import com.team.temara.databinding.ProfileFragmentBinding
import com.team.temara.ui.main.MainActivity
import com.team.temara.ui.profil.panicbutton.PanicButtonActivity
import com.team.temara.ui.profil.password.PasswordActivity
import com.team.temara.ui.profil.personaldata.PersonalDataActivity

class ProfileFragment : Fragment() {
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    private val profileFragmentViewModel: ProfileFragmentViewModel by viewModels {
        ProfileFragmentViewModel.ProfileViewModelFactory.getInstance(requireContext())
    }

    private var isDataLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvPersonalData.setOnClickListener {
            startActivity(Intent(context, PersonalDataActivity::class.java))
        }
        binding.btnSos.setOnClickListener {
            startActivity(Intent(context, PanicButtonActivity::class.java))
        }
        binding.tvUbahSandi.setOnClickListener {
            startActivity(Intent(context, PasswordActivity::class.java))
        }
        binding.btnLogout.setOnClickListener {
            profileFragmentViewModel.logout()
        }

        if (!isDataLoaded) { // Check if data is not loaded yet
            profileFragmentViewModel.checkToken().observe(viewLifecycleOwner) { token ->
                if (token != "null") {
                    val myToken = "Bearer $token"
                    profileFragmentViewModel.checkId().observe(viewLifecycleOwner) { userId ->
                        val myId = userId ?: ""
                        profileFragmentViewModel.getUser(myToken, myId).observe(viewLifecycleOwner) {
                            when (it) {
                                is Result.Loading -> {
                                    binding.progreesBar.visibility = View.VISIBLE
                                }
                                is Result.Error -> {
                                    binding.progreesBar.visibility = View.GONE
                                }
                                is Result.Success -> {
                                    binding.progreesBar.visibility = View.GONE
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
                isDataLoaded = true
            }
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
