package com.team.temara.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.team.temara.databinding.ProfileFragmentBinding
import com.team.temara.ui.profil.password.PasswordActivity
import com.team.temara.ui.profil.personaldata.PersonalDataActivity

class ProfileFragment : Fragment() {
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

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
        binding.apply {
            tvPersonalData.setOnClickListener {
                startActivity(Intent(context, PersonalDataActivity::class.java))
            }
        }
        binding.apply {
            tvUbahSandi.setOnClickListener {
                startActivity(Intent(context, PasswordActivity::class.java))
            }
        }
    }
}