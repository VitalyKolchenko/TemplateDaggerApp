package com.example.anotherdaggerapp.login

class ValidationError(val types: Set<Type>) : Throwable(){
    enum class Type {
        EMPTY_EMAIL, EMPTY_PASSWORD, INVALID_EMAIL, WRONG_PASSWORD
    }
}