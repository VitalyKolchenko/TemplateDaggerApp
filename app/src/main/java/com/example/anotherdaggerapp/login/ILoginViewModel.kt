package com.example.anotherdaggerapp.login

import com.example.anotherdaggerapp.LceLiveData
import com.example.anotherdaggerapp.data.LoginResponse

interface ILoginViewModel {
    fun doLogin(login: String, password: String)
    val loginResult: LceLiveData<LoginResponse>
}