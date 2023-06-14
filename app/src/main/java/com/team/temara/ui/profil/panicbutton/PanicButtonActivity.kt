package com.team.temara.ui.profil.panicbutton

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.team.temara.databinding.PanicButtonActivityBinding

class PanicButtonActivity : AppCompatActivity() {

    private val binding: PanicButtonActivityBinding by lazy {
        PanicButtonActivityBinding.inflate(layoutInflater)
    }

    private val contactPhoneNumber = "119"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btn119.setOnClickListener {
            checkCallPermission()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun checkCallPermission() {
        val permission = Manifest.permission.CALL_PHONE
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            makeCall()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission), CALL_PERMISSION_REQUEST_CODE)
        }
    }

    private fun makeCall() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$contactPhoneNumber"))
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall()
            }
        }
    }
    companion object {
        private const val CALL_PERMISSION_REQUEST_CODE = 1
    }
}
