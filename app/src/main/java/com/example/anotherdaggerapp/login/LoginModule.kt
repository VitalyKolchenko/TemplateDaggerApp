package com.example.anotherdaggerapp.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.anotherdaggerapp.api.ILoginApi
import com.example.anotherdaggerapp.utils.cast
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module()
open class LoginModule(val activity: LoginActivity) {

    @Provides
    open fun viewModel(@Named("stub") loginApi: ILoginApi): ILoginViewModel = ViewModelProviders.of(activity,
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(loginApi, activity.application).cast()
            }
        }).get(LoginViewModel::class.java)

}