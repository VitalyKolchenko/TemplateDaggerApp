package com.example.anotherdaggerapp.login

import dagger.Subcomponent

interface BaseLoginComponent {
    fun inject(loginActivity: LoginActivity)
}

@Subcomponent(modules = [LoginModule::class])
interface LoginComponent: BaseLoginComponent{

}