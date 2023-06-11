package com.team.temara.ui.main.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.team.temara.databinding.ProfileFragmentBinding
import com.team.temara.ui.info.InfoUserActivity
import com.team.temara.ui.profil.panicbutton.PanicButtonActivity
import com.team.temara.ui.profil.password.PasswordActivity
import com.team.temara.ui.profil.personaldata.PersonalDataActivity

class ProfileFragment : Fragment() {
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    private val profileFragmentViewModel: ProfileFragmentViewModel by viewModels {
        ProfileFragmentViewModel.ProfileViewModelFactory.getInstance(requireContext())
    }

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
        binding.tvUbahSandi.setOnClickListener{
            startActivity(Intent(context, PasswordActivity::class.java))
        }
        binding.btnLogout.setOnClickListener {
            profileFragmentViewModel.logout()
        }

    }

}
