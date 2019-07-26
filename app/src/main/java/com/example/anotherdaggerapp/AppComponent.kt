package com.example.anotherdaggerapp

import com.example.anotherdaggerapp.login.LoginComponent
import com.example.anotherdaggerapp.login.LoginModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun newLoginComponent(module: LoginModule): LoginComponent
}




