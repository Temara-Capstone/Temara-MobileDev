package com.team.temara.ui.profil.personaldata

import android.app.DatePickerDialog
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.team.temara.data.remote.response.Result
import com.team.temara.databinding.PersonalDataActivityBinding
import com.team.temara.ui.login.LoginActivity
import com.team.temara.utils.DateFormatter
import com.team.temara.utils.uriToFile
import java.io.File
import java.util.Calendar
import java.util.TimeZone

class PersonalDataActivity : AppCompatActivity() {

    private val binding: PersonalDataActivityBinding by lazy {
        PersonalDataActivityBinding.inflate(layoutInflater)
    }

    private val personalDataViewModel: PersonalDataViewModel by viewModels {
        PersonalDataViewModel.PersonalDataViewModelFactory.getInstance(this)
    }

    private var isDataLoaded = false
    private var getUserFile: File? = null



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnGallery.setOnClickListener {
            openGallery()
        }

        binding.btnSave.setOnClickListener {
            personalDataViewModel.checkToken().observe(this) { token ->
                if (token != "null") {
                    val myToken = "Bearer $token"
                    personalDataViewModel.checkId().observe(this) { userId ->
                        val myId = userId ?: ""
                        updateData(myToken, myId)
                    }
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }


        if (!isDataLoaded) {


            personalDataViewModel.checkToken().observe(this) { token ->
                if (token != "null") {
                    val myToken = "Bearer $token"
                    personalDataViewModel.checkId().observe(this) { userId ->
                        val myId = userId ?: ""

                        personalDataViewModel.getUser(myToken, myId).observe(this) {
                            when (it) {
                                is Result.Loading -> {
                                }

                                is Result.Error -> {
                                }

                                is Result.Success -> {
                                    val result = it.result
                                    binding.etFullName.text = Editable.Factory.getInstance().newEditable(result.name)
                                    binding.etEmail.text = Editable.Factory.getInstance().newEditable(result.email)
                                    binding.etNohp.text = Editable.Factory.getInstance().newEditable(result.no_hp)
                                    binding.btnBorn.text = DateFormatter.formatDate(result.dateofbirth, TimeZone.getDefault().id)


                                    if (result.gender == "Pria") {
                                        binding.rbPria.isChecked = true
                                    } else {
                                        binding.rbWanita.isChecked = true
                                    }

                                    Glide.with(this)
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


        binding.btnBorn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    val selectedDate = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
                    binding.btnBorn.text = selectedDate
                },
                year,
                month,
                dayOfMonth
            )

            dpd.show()
        }


    }

    override fun onResume() {
        super.onResume()
        isDataLoaded = false
    }

    private fun updateData(token: String, userId: String) {
        val name = binding.etFullName.text.toString()
        val email = binding.etEmail.text.toString()
        val gender = if (binding.rbPria.isChecked) "Pria" else "Wanita"
        val dateOfBirth = binding.btnBorn.text.toString()
        val phoneNumber = binding.etNohp.text.toString()

        val updatedPhoneNumber = phoneNumber ?: ""

        personalDataViewModel.updateUser(token, userId, name, email, dateOfBirth, gender, updatedPhoneNumber).observe(this) {
            when (it) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(it.resultCode == RESULT_OK) {
            val imgChoose: Uri = it.data?.data as Uri
            val file = uriToFile(imgChoose, this)
            getUserFile = file
            binding.ivUser.setImageURI(imgChoose)
        }
    }

    private fun openGallery() {
        val galIntent = Intent()
        galIntent.action = ACTION_GET_CONTENT
        galIntent.type = "image/*"
        val choose = Intent.createChooser(galIntent, "Select Photo")
        launchGallery.launch(choose)
    }


}