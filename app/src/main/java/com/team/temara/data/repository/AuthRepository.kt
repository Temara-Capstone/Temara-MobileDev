package com.team.temara.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.team.temara.Utils.Event
import com.team.temara.data.remote.response.Data
import com.team.temara.data.remote.response.LoginResponse
import com.team.temara.data.remote.response.RegisterResponse
import com.team.temara.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {

    private val login = MutableLiveData<LoginResponse>()

    private val _registerUser = MutableLiveData<RegisterResponse>()
    val registerUser: LiveData<RegisterResponse> = _registerUser

    private val _loginUser = MutableLiveData<Data>()
    val loginUser: LiveData<Data> = _loginUser

    private val _isEnabled = MutableLiveData<Boolean>()
    val isEnabled: LiveData<Boolean> = _isEnabled

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _regMsg = MutableLiveData<Event<String>>()
    val regMsg: LiveData<Event<String>>
        get() = _regMsg

    private val _logMsg = MutableLiveData<Event<String>>()
    val logMsg: LiveData<Event<String>>
        get() = _logMsg


    fun login(
        email: String,
        password: String
    ): LiveData<LoginResponse> {
        _isEnabled.value = false
        _isLoading.value = true

        ApiConfig.getApiService().login(email, password)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    _isEnabled.value = true
                    _isLoading.value = false

                    if(response.isSuccessful) {
                        response.body().let { login ->
                            login?.data?.let {
                                _loginUser.value = Data(it.userId, it.token)
                            }
                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                        _logMsg.value = Event("")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            }
        )
        return login
    }

    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<RegisterResponse> {
        _isEnabled.value = false
        _isLoading.value = true

        ApiConfig.getApiService().register(name, email, password)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    _isEnabled.value = true
                    _isLoading.value = false

                    if (response.isSuccessful) {
                        _registerUser.postValue(response.body())
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                        _regMsg.value = Event("")
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        return _registerUser
    }

    companion object {
        private const val TAG = "AuthRepository"
    }

}