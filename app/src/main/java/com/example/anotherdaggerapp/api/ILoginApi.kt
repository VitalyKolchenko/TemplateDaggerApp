package com.example.anotherdaggerapp.api

import com.example.anotherdaggerapp.data.LoginRequest
import com.example.anotherdaggerapp.data.LoginResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ILoginApi {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Observable<LoginResponse>
}