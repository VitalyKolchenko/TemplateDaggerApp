package com.example.anotherdaggerapp

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.example.anotherdaggerapp.api.ILoginApi
import com.example.anotherdaggerapp.data.LoginRequest
import com.example.anotherdaggerapp.data.LoginResponse
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun mapper(): ObjectMapper = ObjectMapper().registerModule(KotlinModule())

    @Singleton
    @Provides
    fun http(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()

    @Singleton
    @Provides
    fun retrofit(client: OkHttpClient, mapper: ObjectMapper): Retrofit =
        Retrofit.Builder().baseUrl("http://example.com:8000")
            .client(client)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    fun loginApi(retrofit: Retrofit): ILoginApi = retrofit.create(ILoginApi::class.java)

    @Provides
    @Named("stub")
    fun loginStubApi(): ILoginApi = object : ILoginApi {
        override fun login(request: LoginRequest): Observable<LoginResponse> {
            val obs = if (request.login == "login1@a.ru" && request.pass == "pass1")
                Observable.just(LoginResponse("TRUE_TOKEN"))
            else
                Observable.error(Throwable("Login Error"))

            return obs.delay(5L, TimeUnit.SECONDS)
        }
    }
}