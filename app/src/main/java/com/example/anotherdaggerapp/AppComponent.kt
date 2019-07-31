package com.example.anotherdaggerapp

import com.example.anotherdaggerapp.content.ContentModule
import com.example.anotherdaggerapp.content.ContentComponent
import com.example.anotherdaggerapp.login.LoginComponent
import com.example.anotherdaggerapp.login.LoginModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun newLoginComponent(module: LoginModule): LoginComponent
    fun newContentComponent(module: ContentModule): ContentComponent
    fun newMvpContentComponent(module: com.example.anotherdaggerapp.mvpcontent.ContentModule): com.example.anotherdaggerapp.mvpcontent.ContentComponent
}




