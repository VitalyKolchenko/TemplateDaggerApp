package com.example.anotherdaggerapp.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.anotherdaggerapp.LceLiveData
import com.example.anotherdaggerapp.Result
import com.example.anotherdaggerapp.Status
import com.example.anotherdaggerapp.api.ILoginApi
import com.example.anotherdaggerapp.data.LoginRequest
import com.example.anotherdaggerapp.data.LoginResponse
import com.example.anotherdaggerapp.utils.async
import com.example.anotherdaggerapp.validation.CompositeValidator
import com.example.anotherdaggerapp.validation.EmailValidator
import com.example.anotherdaggerapp.validation.EmptyValidator

class LoginViewModel(private val loginApi: ILoginApi, application: Application) : AndroidViewModel(application), ILoginViewModel {

    override fun doLogin(login: String, password: String) {
        if (validate(login, password))
            loginApi.login(LoginRequest(login, password)).async().subscribe(loginResult.rxObserver())

    }

    override val loginResult: LceLiveData<LoginResponse> =
        LceLiveData()

    private fun validate(login: String, password: String): Boolean {

        val compositeValidator = CompositeValidator(
            Pair(EmailValidator(login), ValidationError.Type.INVALID_EMAIL),
            Pair(EmptyValidator(login), ValidationError.Type.EMPTY_EMAIL),
            Pair(EmptyValidator(password), ValidationError.Type.EMPTY_PASSWORD)

        )

        if (!compositeValidator.isValid) {
            loginResult.value = Result(
                status = Status.ERROR,
                error = ValidationError(compositeValidator.errors)
            )
            return false
        }
        return true
    }
}